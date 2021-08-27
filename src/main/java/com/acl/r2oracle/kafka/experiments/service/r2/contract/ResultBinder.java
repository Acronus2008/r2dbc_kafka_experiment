package com.acl.r2oracle.kafka.experiments.service.r2.contract;

import com.acl.r2oracle.kafka.experiments.service.r2.datasource.domain.R2Example;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.enums.RedisResult;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.R2ExampleTO;
import io.r2dbc.spi.Row;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.reactivestreams.Publisher;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;

@Mapper(imports = RedisResult.class)
public interface ResultBinder {
    ResultBinder RESULT_BINDER = Mappers.getMapper(ResultBinder.class);

    R2ExampleTO bind(R2Example source);

    default R2Example bind(Row source) {
        R2Example target = new R2Example();
        target.setId(source.get(0, Long.class));
        target.setName(source.get(1, String.class));
        target.setLastName(source.get(2, String.class));
        target.setRut(source.get(4, String.class));
        target.setBirthDate(source.get(3, LocalDateTime.class));
        return target;
    }

    default R2ExampleTO bind(Tuple2<Publisher<R2Example>, R2Example> objects) {
        return this.bind(objects.getT2());
    }
}
