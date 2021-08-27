package com.acl.r2oracle.kafka.experiments.service.xml.datasource;

import com.acl.r2oracle.kafka.experiments.core.mapper.ReactiveMapper;
import com.acl.r2oracle.kafka.experiments.service.xml.datasource.domain.ExperimentEntity;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

public class ExperimentMapper implements ReactiveMapper<ExperimentEntity> {
    @Override
    public ExperimentEntity map(Row row, RowMetadata metadata) {
        ExperimentEntity entity = new ExperimentEntity();
        entity.setId(row.get(0, Long.class));
        entity.setCreatedDate(row.get(1, String.class));
        entity.setProductId(row.get(2, String.class));
        entity.setStock(row.get(3, Long.class));
        return entity;
    }
}
