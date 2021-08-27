package com.acl.r2oracle.kafka.experiments.dist.rest;

import com.acl.r2oracle.kafka.experiments.core.facade.controllers.Controller;
import com.acl.r2oracle.kafka.experiments.facade.r2.HealthCheckFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cache/health")
public class HealthCheckController extends Controller<HealthCheckFacade> {

    @GetMapping("/readiness")
    public Mono<ResponseEntity> readiness() {
        return this.get(HealthCheckFacade::getReadiness);
    }

    @GetMapping("/liveness")
    public Mono<ResponseEntity> liveness() {
        return this.get(HealthCheckFacade::getLiveness);
    }
}
