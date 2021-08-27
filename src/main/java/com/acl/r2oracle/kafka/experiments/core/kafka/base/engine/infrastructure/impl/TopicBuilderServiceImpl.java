package com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.impl;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.configuration.KafkaProperties;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.TopicBuilderService;

/**
 * Default implementation of {@link TopicBuilderService}.
 *
 * @since 0.1.0
 */
public class TopicBuilderServiceImpl implements TopicBuilderService {
    private final KafkaProperties kafkaProperties;

    public TopicBuilderServiceImpl(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Override
    public String getPrefix() {
        return kafkaProperties.getTopicPrefix();
    }

    @Override
    public String getEnviroment() {
        return kafkaProperties.getEnviromentPrefix();
    }
}
