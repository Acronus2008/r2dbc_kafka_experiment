package com.acl.r2oracle.kafka.experiments.dist.rest;

import com.acl.r2oracle.kafka.experiments.core.facade.controllers.Controller;
import com.acl.r2oracle.kafka.experiments.facade.r2.R2Facade;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.R2ExampleTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/r2dbc")
public class R2ExampleController extends Controller<R2Facade> {

    @PostMapping("/")
    public Mono<ResponseEntity> create(@RequestBody R2ExampleTO request) {
        return this.perform(facade -> facade.create(request));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity> load(@PathVariable String id) {
        return this.get(facade -> facade.load(id));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity> update(@PathVariable String id, @RequestBody R2ExampleTO request) {
        return this.perform(facade -> facade.update(request.withId(id)));
    }

    @GetMapping("/all")
    public Mono<ResponseEntity> loadAll() {
        return this.get(R2Facade::loadAll);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity> delete(@PathVariable String id) {
        return this.perform(facade -> facade.delete(id));
    }
}
