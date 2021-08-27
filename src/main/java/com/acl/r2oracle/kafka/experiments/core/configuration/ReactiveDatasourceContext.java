package com.acl.r2oracle.kafka.experiments.core.configuration;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;


@Configuration
@EnableR2dbcRepositories
public class ReactiveDatasourceContext {

    private final DatabaseConnectionContext connectionContext;

    public ReactiveDatasourceContext(DatabaseConnectionContext connectionContext) {
        this.connectionContext = connectionContext;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.parse(this.connectionContext.getDatabaseUrl())
                .mutate()
                .option(ConnectionFactoryOptions.USER, this.connectionContext.getUser())
                .option(ConnectionFactoryOptions.PASSWORD, this.connectionContext.getPassword())
                .build());
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}
