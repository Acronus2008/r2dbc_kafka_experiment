package com.acl.r2oracle.kafka.experiments.core.kafka.reactive;

import com.acl.r2oracle.kafka.experiments.core.exceptions.RootException;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.QueueManagerService;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.TopicBuilderService;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.configuration.KafkaProperties;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.messages.StandardMessage;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.acl.r2oracle.kafka.experiments.core.kafka.reactive.KafkaUtil.getReceiverProperties;
import static com.acl.r2oracle.kafka.experiments.core.kafka.reactive.KafkaUtil.getSenderProperties;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.fromRunnable;
import static reactor.core.publisher.Mono.just;
import static reactor.kafka.sender.SenderRecord.create;

public class ReactiveKafkaEngine {
    private static final Logger logger = getLogger(ReactiveKafkaEngine.class);
    private final Set<String> initializedTopics;
    private final KafkaProperties kafkaProperties;
    private final TopicBuilderService topicBuilder;
    private final QueueManagerService queueAdminService;
    private Map<String, Disposable> operators;
    private KafkaSender<String, Envelope<StandardMessage>> sender;
    private UnicastProcessor<Envelope<StandardMessage>> source;

    public ReactiveKafkaEngine(KafkaProperties kafkaProperties, TopicBuilderService topicBuilder, QueueManagerService queueAdminService) {
        this.kafkaProperties = kafkaProperties;
        this.topicBuilder = topicBuilder;
        this.queueAdminService = queueAdminService;
        this.operators = new HashMap<>();
        this.initializedTopics = new HashSet<>();
    }

    //@PreDestroy
    public void stop() {
        if (null != this.sender) {
            this.sender.close();
        }
        if (null != this.source) {
            this.source.onComplete();
        }
        this.operators.values().forEach(Disposable::dispose);
    }

    public void createFlow(String topic, Function<Envelope<StandardMessage>, Publisher<Envelope<StandardMessage>>> flowOperation) throws Exception {
        this.initializeQueue(topic);
        this.initializeSource();
        ReceiverOptions<String, Envelope<StandardMessage>> options = ReceiverOptions.<String, Envelope<StandardMessage>>create(getReceiverProperties(kafkaProperties))
                .subscription(singleton(getTopic(topic)));
        Disposable pipeline = KafkaReceiver.create(options)
                .receive()
                .flatMap(record -> this.applyFlow(record, flowOperation), kafkaProperties.getConcurrency())
                .map(message -> create(new ProducerRecord<>(this.getTopic(message), message.getId(), message), message))
                .as(sender::send)
                .onErrorResume(ex -> fromRunnable(() -> logger.error("Error detected when trying to process a (kafka) producer record", ex)))
                .subscribe();
        operators.put(getTopic(topic), pipeline);
    }

    public <T> void createSink(String topic, Function<Envelope<StandardMessage>, Mono<T>> sinkOperation) throws Exception {
        this.initializeQueue(topic);
        ReceiverOptions<String, Envelope<StandardMessage>> options = ReceiverOptions.<String, Envelope<StandardMessage>>create(getReceiverProperties(kafkaProperties))
                .subscription(singleton(getTopic(topic)));
        Disposable pipeline = KafkaReceiver.create(options).receive()
                .flatMap(record -> this.applySink(record, sinkOperation), kafkaProperties.getConcurrency())
                .onErrorResume(ex -> fromRunnable(() -> logger.error("Error detected when trying to process a (kafka) receiver record", ex)))
                .subscribe(ReceiverOffset::acknowledge);
        operators.put(getTopic(topic), pipeline);
    }

    public void send(Envelope<StandardMessage> message) throws Exception {
        this.initializeQueue(message.getTopic());
        this.initializeSource();
        this.source.onNext(message);
    }

    public Subscriber<Envelope<StandardMessage>> getSubscriber() {
        return new KafkaSubscriber();
    }

    //<editor-fold desc="Support methods">
    private void initializeQueue(String topic) throws Exception {
        if (this.initializedTopics.contains(topic)) {
            return;
        }
        this.initializedTopics.addAll(queueAdminService.getQueues().get());
        if (!this.initializedTopics.contains(topic)) {
            queueAdminService.createQueues(singletonList(topic));
            this.initializedTopics.add(topic);
        }
    }

    private void initializeSource() {
        if (null == this.sender) {
            SenderOptions<String, Envelope<StandardMessage>> options = SenderOptions.<String, Envelope<StandardMessage>>create(getSenderProperties(kafkaProperties))
                    .maxInFlight(1024);//TODO Check why this param
            this.sender = KafkaSender.create(options);
        }
        if (null == this.source) {
            this.source = UnicastProcessor.create();
            Flux<SenderRecord<String, Envelope<StandardMessage>, Envelope<StandardMessage>>> records = this.source
                    .map(message -> create(new ProducerRecord<>(getTopic(message.getTopic()), message.getId(), message), message))
                    .onErrorResume(ex -> fromRunnable(() -> logger.error("Error detected when trying to publish a (kafka) producer record", ex)));
            this.sender.send(records).subscribe();
        }
    }

    private <T> Mono<ReceiverOffset> applySink(ReceiverRecord<String, Envelope<StandardMessage>> record, Function<Envelope<StandardMessage>, Mono<T>> sinkFunction) {
        return just(record)
                .map(ReceiverRecord::value)
                .switchIfEmpty(Mono.error(new RootException("Resolved 'null' value from a kafka record!")))
                .flatMap(sinkFunction)
                .thenReturn(record.receiverOffset());
    }

    private Flux<Envelope<StandardMessage>> applyFlow(ReceiverRecord<String, Envelope<StandardMessage>> record, Function<Envelope<StandardMessage>, Publisher<Envelope<StandardMessage>>> flowFunction) {
        return Flux.just(record)
                .map(ReceiverRecord::value)
                .switchIfEmpty(error(new RootException("Resolved 'null' value from a kafka record!")))
                .flatMap(flowFunction)
                .onErrorResume(ex -> fromRunnable(() -> logger.error("Error detected when trying to process a (kafka) receiver record", ex)))
                .doOnComplete(() -> record.receiverOffset().acknowledge());
    }

    private String getTopic(Envelope<?> envelope) {
        return this.getTopic(envelope.getTopic());
    }

    private String getTopic(String topic) {
        return topicBuilder.buildTopic(topic);
    }
    //</editor-fold>

    //<editor-fold desc="Inner classes">
    private class KafkaSubscriber implements Subscriber<Envelope<StandardMessage>> {
        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(kafkaProperties.getConcurrency());
        }

        @Override
        public void onNext(Envelope<StandardMessage> message) {
            source.onNext(message);
            this.subscription.request(kafkaProperties.getConcurrency());
        }

        @Override
        public void onError(Throwable tw) {
            logger.error("Error received from the source this subscriber is subscribed to.", tw);
        }

        @Override
        public void onComplete() {
            logger.trace("Subscription successfully completed.");
        }
    }
    //</editor-fold>
}
