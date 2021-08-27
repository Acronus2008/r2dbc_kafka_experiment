package com.acl.r2oracle.kafka.experiments.service.xml.datasource.impl;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.messages.StandardMessage;
import com.acl.r2oracle.kafka.experiments.core.kafka.reactive.ReactiveKafkaEngine;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ItemTO;
import com.acl.r2oracle.kafka.experiments.service.xml.datasource.OrchestratorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

import static com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.messages.StandardMessage.fromHeaders;
import static java.util.Collections.emptyMap;

@Repository
public class KafkaOrchestratorRepository implements OrchestratorRepository {

    private final Function<ItemTO, StandardMessage> getMessage = item -> fromHeaders(emptyMap()).withContent(item);
    private final ReactiveKafkaEngine reactiveKafkaEngine;
    private final String topic;

    public KafkaOrchestratorRepository(ReactiveKafkaEngine reactiveKafkaEngine, @Value("${app.kafka.output.r2}") String topic) {
        this.reactiveKafkaEngine = reactiveKafkaEngine;
        this.topic = topic;
    }

    @Override
    public Mono<Void> orchestrate(List<ItemTO> items) {
        return Flux.fromIterable(items)
                .doOnNext(this::doSend)
                .then();
    }

    //<editor-fold desc="Support methods">
    private void doSend(ItemTO delta) {
        try {
            this.reactiveKafkaEngine.send(Envelope.<StandardMessage>from(topic).withMessage(this.getMessage.apply(delta)));
        } catch (Exception ex) {
            throw new RuntimeException("An error has occurred when trying to save a read data", ex);
        }
    }
}
