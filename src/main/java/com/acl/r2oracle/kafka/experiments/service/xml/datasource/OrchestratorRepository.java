package com.acl.r2oracle.kafka.experiments.service.xml.datasource;

import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ItemTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrchestratorRepository {
    Mono<Void> orchestrate(List<ItemTO> items);
}
