package com.acl.r2oracle.kafka.experiments.facade.r2;

import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.HealthCheckTO;
import reactor.core.publisher.Mono;

/**
 * Default interface for {@link HealthCheckFacade}
 * controll the health of micro service
 *
 * @author Alejandro
 * @since 0.1.0
 */
public interface HealthCheckFacade {
    Mono<Result<HealthCheckTO>> getReadiness();

    Mono<Result<HealthCheckTO>> getLiveness();
}
