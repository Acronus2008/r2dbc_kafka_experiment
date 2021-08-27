package com.acl.r2oracle.kafka.experiments.core.mapper;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

@FunctionalInterface
public interface ReactiveMapper<T> {
    T map(Row row, RowMetadata metadata);
}
