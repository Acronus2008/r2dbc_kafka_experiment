package com.acl.r2oracle.kafka.experiments.service.xml;

import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ExperimentResponseTO;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ItemTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExperimentService {
    Mono<Void> read();

    Mono<Void> create(ItemTO item);

    Mono<ExperimentResponseTO> load(String id);

    Flux<ExperimentResponseTO> loadAll();
}
