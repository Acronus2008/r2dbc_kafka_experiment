package com.acl.r2oracle.kafka.experiments.service.r2.datasource;

import com.acl.r2oracle.kafka.experiments.core.mapper.ReactiveMapper;
import com.acl.r2oracle.kafka.experiments.service.r2.datasource.domain.R2Example;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.time.LocalDateTime;

public class R2MapperExample implements ReactiveMapper<R2Example> {
    @Override
    public R2Example map(Row row, RowMetadata metadata) {
        R2Example target = new R2Example();
        target.setId(row.get(0, Long.class));
        target.setName(row.get(1, String.class));
        target.setLastName(row.get(2, String.class));
        target.setBirthDate(row.get(3, LocalDateTime.class));
        target.setRut(row.get(4, String.class));
        return target;
    }
}
