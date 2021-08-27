package com.acl.r2oracle.kafka.experiments.service.xml.contract;

import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ExperimentResponseTO;
import com.acl.r2oracle.kafka.experiments.service.xml.datasource.domain.ExperimentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExperimentBinder {
    ExperimentBinder EXPERIMENT_BINDER = Mappers.getMapper(ExperimentBinder.class);

    @Mapping(target = "date", source = "createdDate")
    @Mapping(target = "stock", expression = "java(source.getStock().intValue())")
    ExperimentResponseTO bind(ExperimentEntity source);
}
