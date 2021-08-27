package com.acl.r2oracle.kafka.experiments.facade.r2;

import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ItemTO;
import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import reactor.core.publisher.Mono;

/**
 * Default interface for {@link ProcessorFacade}
 * Make operations to process experiment
 *
 * @author Alejandro
 * @since 0.1.0
 */
public interface ProcessorFacade {
    /**
     * Orchestrate an experiment processor to extract xml data and send result to other operator
     *
     * @return {@link Result} the result of experiment processor, can be success or failed
     */
    Mono<Result<Void>> execute();

    /**
     * Orchestrate the operator that receive the experiment result
     *
     * @param item {@link ItemTO} the item recieved throw operator
     * @return {@link Result} the result of experiment operator, can be success or failed
     */
    Mono<Result<Void>> create(ItemTO item);
}
