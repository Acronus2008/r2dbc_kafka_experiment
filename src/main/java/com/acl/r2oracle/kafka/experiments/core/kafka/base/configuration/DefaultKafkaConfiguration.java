package com.acl.r2oracle.kafka.experiments.core.kafka.base.configuration;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.QueueManagerService;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.TopicBuilderService;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.impl.KafkaQueueManagerServiceImpl;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.impl.TopicBuilderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Base configuration class for the kafka context.
 *
 * @since 0.1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.cencosud.puntos.r2oracle.core.kafka.base.configuration"})
public class DefaultKafkaConfiguration {
    @Bean
    public TopicBuilderService topicBuilder(KafkaProperties properties) {
        return new TopicBuilderServiceImpl(properties);
    }

    @Bean
    public QueueManagerService queueAdminService(KafkaProperties kafkaProperties, TopicBuilderService topicBuilderService) {
        KafkaQueueManagerServiceImpl queueAdminService = new KafkaQueueManagerServiceImpl(kafkaProperties, topicBuilderService);
        queueAdminService.init();
        return queueAdminService;
    }
}
