package com.acl.r2oracle.kafka.experiments.core.kafka.reactive.configuration;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.configuration.DefaultKafkaConfiguration;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.QueueManagerService;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.TopicBuilderService;
import com.acl.r2oracle.kafka.experiments.core.kafka.reactive.ReactiveKafkaEngine;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.configuration.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration class for the <i>reactive</i> kafka application context.
 *
 * @author Alejandro
 * @since 0.1.0
 */
@Configuration
@Import(DefaultKafkaConfiguration.class)
public class ReactiveKafkaConfiguration {
    @Bean
    public ReactiveKafkaEngine testReactiveMessageQueue(KafkaProperties properties, TopicBuilderService topicBuilder, QueueManagerService queueAdminService) {
        return new ReactiveKafkaEngine(properties, topicBuilder, queueAdminService);
    }
}
