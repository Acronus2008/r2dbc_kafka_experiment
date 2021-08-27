package com.acl.r2oracle.kafka.experiments.facade.r2;

import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ExperimentResponseTO;
import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Default interface for {@link ExperimentFacade}
 * Make operations over product experiments
 *
 * @author Alejandro
 * @since 0.1.0
 */
public interface ExperimentFacade {
    /**
     * Load an experiment product item
     *
     * @param id {@link String} the product item id
     * @return {@link Result} result with product experiment
     */
    Mono<Result<ExperimentResponseTO>> load(String id);

    /**
     * Load all the experiment products
     *
     * @return {@link Result} the result list with experiment
     */
    Mono<Result<List<ExperimentResponseTO>>> loadAll();
}
