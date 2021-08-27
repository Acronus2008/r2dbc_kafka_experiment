package com.acl.r2oracle.kafka.experiments.facade.r2.impl;

import com.acl.r2oracle.kafka.experiments.core.facade.stereotype.Facade;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ExperimentResponseTO;
import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import com.acl.r2oracle.kafka.experiments.facade.r2.ExperimentFacade;
import com.acl.r2oracle.kafka.experiments.service.xml.ExperimentService;
import reactor.core.publisher.Mono;

import java.util.List;

@Facade("experimentFacade")
public class ExperimentFacadeImpl implements ExperimentFacade {
    private final ExperimentService experimentService;

    public ExperimentFacadeImpl(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @Override
    public Mono<Result<ExperimentResponseTO>> load(String id) {
        return this.experimentService.load(id)
                .map(Result::successful);
    }

    @Override
    public Mono<Result<List<ExperimentResponseTO>>> loadAll() {
        return this.experimentService.loadAll()
                .collectList()
                .map(Result::successful);
    }
}
