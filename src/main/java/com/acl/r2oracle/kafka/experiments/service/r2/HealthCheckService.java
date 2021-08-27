package com.acl.r2oracle.kafka.experiments.service.r2;

import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.HealthCheckTO;
import reactor.core.publisher.Mono;

public interface HealthCheckService {
    Mono<HealthCheckTO> getReadiness();

    Mono<HealthCheckTO> getLiveness();
}
