package com.acl.r2oracle.kafka.experiments.facade.r2;

import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.R2ExampleTO;
import reactor.core.publisher.Mono;

import java.util.List;


public interface R2Facade {
    Mono<Result<Void>> create(R2ExampleTO request);

    Mono<Result<R2ExampleTO>> load(String id);

    Mono<Result<Void>> update(R2ExampleTO request);

    Mono<Result<Void>> delete(String id);

    Mono<Result<List<R2ExampleTO>>> loadAll();
}
