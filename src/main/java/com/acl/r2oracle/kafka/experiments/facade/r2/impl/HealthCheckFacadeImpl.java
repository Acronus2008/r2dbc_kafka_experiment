package com.acl.r2oracle.kafka.experiments.facade.r2.impl;

import com.acl.r2oracle.kafka.experiments.core.facade.stereotype.Facade;
import com.acl.r2oracle.kafka.experiments.facade.r2.HealthCheckFacade;
import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import com.acl.r2oracle.kafka.experiments.service.r2.HealthCheckService;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.HealthCheckTO;
import reactor.core.publisher.Mono;

@Facade("healthCheckFacade")
public class HealthCheckFacadeImpl implements HealthCheckFacade {
    private final HealthCheckService healthCheckService;

    public HealthCheckFacadeImpl(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @Override
    public Mono<Result<HealthCheckTO>> getReadiness() {
        return this.healthCheckService.getReadiness()
                .map(Result::successful);
    }

    @Override
    public Mono<Result<HealthCheckTO>> getLiveness() {
        return this.healthCheckService.getLiveness()
                .map(Result::successful);
    }
}
