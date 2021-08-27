package com.acl.r2oracle.kafka.experiments.facade.r2.impl;

import com.acl.r2oracle.kafka.experiments.core.facade.stereotype.Facade;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ItemTO;
import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import com.acl.r2oracle.kafka.experiments.facade.r2.ProcessorFacade;
import com.acl.r2oracle.kafka.experiments.service.xml.ExperimentService;
import reactor.core.publisher.Mono;

@Facade("processorFacade")
public class ProcessorFacadeImpl implements ProcessorFacade {

    private final ExperimentService experimentService;

    public ProcessorFacadeImpl(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @Override
    public Mono<Result<Void>> execute() {
        return this.experimentService.read()
                .map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> create(ItemTO item) {
        return this.experimentService.create(item)
                .map(Result::successful);
    }
}
