package com.acl.r2oracle.kafka.experiments.dist.rest;

import com.acl.r2oracle.kafka.experiments.core.facade.controllers.Controller;
import com.acl.r2oracle.kafka.experiments.facade.r2.ExperimentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/experiment")
public class ExperimentController extends Controller<ExperimentFacade> {
    @GetMapping("/{id}")
    public Mono<ResponseEntity> load(@PathVariable String id) {
        return this.get(facade -> facade.load(id));
    }

    @GetMapping("/all")
    public Mono<ResponseEntity> loadAll() {
        return this.get(ExperimentFacade::loadAll);
    }
}
