package com.acl.r2oracle.kafka.experiments.dist.stream;

import com.acl.r2oracle.kafka.experiments.service.xml.contract.XMLBinder;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.messages.StandardMessage;
import com.acl.r2oracle.kafka.experiments.core.kafka.reactive.ReactiveKafkaEngine;
import com.acl.r2oracle.kafka.experiments.core.kafka.stereotypes.KafkaStream;
import com.acl.r2oracle.kafka.experiments.facade.r2.ProcessorFacade;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.Map;

import static reactor.core.publisher.Mono.just;

@KafkaStream("eventProcessorFlow")
public class EventProcessorFlow {

    private final ProcessorFacade facade;

    public EventProcessorFlow(ReactiveKafkaEngine reactiveKafkaEngine, @Value("${app.kafka.output.r2}") String r2Topic, ProcessorFacade facade) throws Exception {
        this.facade = facade;
        reactiveKafkaEngine.createSink(r2Topic, this::receiveExperiments);
    }

    private Mono<Void> receiveExperiments(Envelope<StandardMessage> message) {
        return just(message.getMessage().getContent())
                .filter(content -> content instanceof Map)
                .cast(Map.class)
                .map(XMLBinder.XML_BINDER::bind)
                .flatMap(facade::create)
                .then();
    }
}
