package com.acl.r2oracle.kafka.experiments.service.r2;

import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.R2ExampleTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface R2Service {
    Mono<Void> create(R2ExampleTO request);

    Mono<R2ExampleTO> load(String id);

    Mono<Void> update(R2ExampleTO request);

    Mono<Void> delete(String id);

    Flux<R2ExampleTO> loadAll();
}
