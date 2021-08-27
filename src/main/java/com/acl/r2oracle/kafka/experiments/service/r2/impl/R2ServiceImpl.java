package com.acl.r2oracle.kafka.experiments.service.r2.impl;

import com.acl.r2oracle.kafka.experiments.core.exceptions.CustomNotFoundException;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.ResultBinder;
import com.acl.r2oracle.kafka.experiments.service.r2.datasource.R2MapperExample;
import com.acl.r2oracle.kafka.experiments.service.r2.datasource.domain.R2Example;
import com.acl.r2oracle.kafka.experiments.service.r2.R2Service;
import com.acl.r2oracle.kafka.experiments.service.r2.contract.to.R2ExampleTO;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.from;

@Service("r2Service")
public class R2ServiceImpl implements R2Service {

    private final ConnectionFactory connectionFactory;

    public R2ServiceImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Mono<Void> create(R2ExampleTO request) {
        return from(this.connectionFactory.create())
                .flatMap(connection ->
                        from(connection.createStatement("insert into r2_example (id, name, last_name, birth_date, rut) values (r2_sequence.nextval, :p_name, :p_last_name, :p_birth_date, :p_rut)")
                                .bind("p_name", request.getName())
                                .bind("p_last_name", request.getLastName())
                                .bind("p_birth_date", request.getBirthDate())
                                .bind("p_rut", request.getRut())
                                .execute())
                                .thenEmpty(from(connection.close()))
                );
    }

    @Override
    public Mono<R2ExampleTO> load(String id) {
        return from(this.connectionFactory.create())
                .flatMapMany(connection ->
                        from(connection.createStatement("select * from r2_example where id =:example_id")
                                .bind("example_id", Integer.parseInt(id))
                                .execute())
                                .flatMapMany(result -> result.map((row, metadata) -> new R2MapperExample().map(row, metadata)))
                                .concatWith(from(connection.close()).cast(R2Example.class))
                )
                .switchIfEmpty(Mono.error(new CustomNotFoundException()))
                .map(ResultBinder.RESULT_BINDER::bind)
                .collect(Collectors.reducing((r1, r2) -> r1))
                .map(Optional::get);
    }

    @Override
    public Mono<Void> update(R2ExampleTO request) {
        return from(this.connectionFactory.create())
                .flatMap(connection ->
                        from(connection.createStatement("update r2_example set name=:p_name, last_name=:p_last_name, birth_date=:p_birth_date, rut=:p_rut where id =:example_id")
                                .bind("p_name", request.getName())
                                .bind("p_last_name", request.getLastName())
                                .bind("p_birth_date", request.getBirthDate())
                                .bind("p_rut", request.getRut())
                                .bind("example_id", Integer.parseInt(request.getId()))
                                .execute())
                                .thenEmpty(from(connection.close()))
                );
    }

    @Override
    public Mono<Void> delete(String id) {
        return from(this.connectionFactory.create())
                .flatMap(connection ->
                        from(connection.createStatement("delete from r2_example where id =:example_id")
                                .bind("example_id", Integer.parseInt(id))
                                .execute())
                                .thenEmpty(from(connection.close()))
                );
    }

    @Override
    public Flux<R2ExampleTO> loadAll() {
        return from(this.connectionFactory.create())
                .flatMapMany(connection ->
                        from(connection.createStatement("select * from r2_example").execute())
                                .flatMapMany(result -> result.map((row, metadata) -> new R2MapperExample().map(row, metadata)))
                                .concatWith(from(connection.close()).cast(R2Example.class))
                )
                .map(ResultBinder.RESULT_BINDER::bind);
    }
}
