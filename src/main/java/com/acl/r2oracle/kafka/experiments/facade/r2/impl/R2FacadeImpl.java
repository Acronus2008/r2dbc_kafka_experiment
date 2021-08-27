package com.acl.r2oracle.kafka.experiments.facade.r2.impl;

import com.acl.r2oracle.kafka.experiments.core.facade.ErrorCode;
import com.acl.r2oracle.kafka.experiments.core.facade.stereotype.Facade;
import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import com.acl.r2oracle.kafka.experiments.facade.r2.R2Facade;
import com.acl.r2oracle.kafka.experiments.service.r2.R2Service;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.R2ExampleTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.acl.r2oracle.kafka.experiments.core.Error.err;
import static com.acl.r2oracle.kafka.experiments.core.facade.Result.failed;
import static reactor.core.publisher.Mono.just;

@Facade("r2Facade")
public class R2FacadeImpl implements R2Facade {
    private final Logger logger = LoggerFactory.getLogger(R2Facade.class);

    private final R2Service r2Service;

    public R2FacadeImpl(R2Service r2Service) {
        this.r2Service = r2Service;
    }

    @Override
    public Mono<Result<Void>> create(R2ExampleTO request) {
        return this.r2Service.create(request)
                .map(Result::successful);
    }

    @Override
    public Mono<Result<R2ExampleTO>> load(String id) {
        return this.r2Service.load(id)
                .map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> update(R2ExampleTO request) {
        return this.r2Service.update(request)
                .map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> delete(String id) {
        return this.r2Service.delete(id)
                .map(Result::successful);
    }

    @Override
    public Mono<Result<List<R2ExampleTO>>> loadAll() {
        try {
            return this.r2Service.loadAll()
                    .collectList()
                    .onErrorResume(e -> {
                        logger.error("Error loading all data {}", e.getMessage());
                        return just(new ArrayList<>());
                    })
                    .map(Result::successful);
        } catch (Exception e) {
            return just(failed(err(ErrorCode.INTERNAL_ERROR, e.getMessage())));
        }
    }
}
