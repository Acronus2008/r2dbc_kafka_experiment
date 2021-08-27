package com.acl.r2oracle.kafka.experiments.service.r2.impl;

import com.acl.r2oracle.kafka.experiments.service.r2.HealthCheckService;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.HealthCheckTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static reactor.core.publisher.Mono.just;

@Service("healthCheckService")
public class HealthCheckServiceImpl implements HealthCheckService {
    private static final String READINESS_MESSAGE = "Readiness OK";
    private static final String LEVENESS_MESSAGE = "Liveness OK";
    private final Function<String, HealthCheckTO> healthResponse = HealthCheckTO::withResult;


    @Override
    public Mono<HealthCheckTO> getReadiness() {
        return just(this.healthResponse.apply(READINESS_MESSAGE));
    }

    @Override
    public Mono<HealthCheckTO> getLiveness() {
        return just(this.healthResponse.apply(LEVENESS_MESSAGE));
    }
}
