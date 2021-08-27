package com.acl.r2oracle.kafka.experiments.configuration;

import com.acl.r2oracle.kafka.experiments.core.configuration.XMLContext;
import com.acl.r2oracle.kafka.experiments.core.configuration.DatabaseConnectionContext;
import com.acl.r2oracle.kafka.experiments.core.configuration.DefaultWebContextConfiguration;
import com.acl.r2oracle.kafka.experiments.core.configuration.ReactiveDatasourceContext;
import com.acl.r2oracle.kafka.experiments.core.kafka.reactive.configuration.ReactiveKafkaConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.acl.r2oracle.kafka.experiments.util.message.MessageContextHolder.source;

@Configuration
@ComponentScan({
        "com.acl.r2oracle.kafka.experiments.dist.rest",
        "com.acl.r2oracle.kafka.experiments.dist.stream",
        "com.acl.r2oracle.kafka.experiments.dist.scheduler",
        "com.acl.r2oracle.kafka.experiments.facade.*.impl",
        "com.acl.r2oracle.kafka.experiments.service.*.impl",
        "com.acl.r2oracle.kafka.experiments.service.*.datasource"
})
@Import({
        DefaultWebContextConfiguration.class,
        ReactiveDatasourceContext.class,
        DatabaseConnectionContext.class,
        ReactiveKafkaConfiguration.class,
        XMLContext.class
})
@EnableScheduling
public class DefaultConfiguration {
    @Bean
    public MessageSource messageSource() {
        return source();
    }
}
