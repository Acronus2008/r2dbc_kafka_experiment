package com.acl.r2oracle.kafka.experiments.service.xml.impl;

import com.acl.r2oracle.kafka.experiments.core.exceptions.CustomNotFoundException;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.ExperimentBinder;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.XMLBinder;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ExperimentResponseTO;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ItemTO;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.XMLDeltaTO;
import com.acl.r2oracle.kafka.experiments.service.xml.datasource.ExperimentMapper;
import com.acl.r2oracle.kafka.experiments.service.xml.datasource.OrchestratorRepository;
import com.acl.r2oracle.kafka.experiments.service.xml.datasource.domain.ExperimentEntity;
import com.acl.r2oracle.kafka.experiments.service.xml.schema.InventoryType;
import com.acl.r2oracle.kafka.experiments.service.xml.ExperimentService;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.from;
import static reactor.core.publisher.Mono.fromCallable;

@Service("xMLService")
public class ExperimentServiceImpl implements ExperimentService {

    private final Logger logger = LoggerFactory.getLogger(ExperimentService.class);

    private final OrchestratorRepository orchestratorRepository;
    private final ConnectionFactory connectionFactory;
    private final Jaxb2Marshaller jaxb2Marshaller;

    public ExperimentServiceImpl(OrchestratorRepository orchestratorRepository, ConnectionFactory connectionFactory, Jaxb2Marshaller jaxb2Marshaller) {
        this.orchestratorRepository = orchestratorRepository;
        this.connectionFactory = connectionFactory;
        this.jaxb2Marshaller = jaxb2Marshaller;
    }

    @Override
    public Mono<Void> read() {
        try {
            return this.unmarshallXml(new DataInputStream(new FileInputStream(new ClassPathResource("xml/delta.xml").getFile())))
                    .cast(JAXBElement.class)
                    .map(JAXBElement::getValue)
                    .cast(InventoryType.class)
                    .map(XMLBinder.XML_BINDER::bind)
                    .flatMap(this::orchestrateDelta);
        } catch (IOException | JAXBException e) {
            logger.error("Error parsing delta xml {}", e.getMessage());
            return Mono.empty();
        }
    }

    @Override
    public Mono<Void> create(ItemTO item) {
        return from(this.connectionFactory.create())
                .flatMap(connection ->
                        from(connection.createStatement("insert into product_item (id, created_date, product_id, stock) values (r2_sequence.nextval, :created_date, :product_id, :stock)")
                                .bind("created_date", item.getDate())
                                .bind("product_id", item.getProductId())
                                .bind("stock", item.getStock())
                                .execute())
                                .thenEmpty(from(connection.close()))
                );
    }

    @Override
    public Mono<ExperimentResponseTO> load(String id) {
        ConnectionFactoryMetadata metadata1 = this.connectionFactory.getMetadata();
        return from(this.connectionFactory.create())
                .flatMapMany(connection ->
                        from(connection.createStatement("select * from product_item where id =:example_id")
                                .bind("example_id", Integer.parseInt(id))
                                .execute())
                                .flatMapMany(result -> result.map((row, metadata) -> new ExperimentMapper().map(row, metadata)))
                                .concatWith(from(connection.close()).cast(ExperimentEntity.class))
                )
                .switchIfEmpty(Mono.error(new CustomNotFoundException()))
                .map(ExperimentBinder.EXPERIMENT_BINDER::bind)
                .collect(Collectors.reducing((r1, r2) -> r1))
                .map(Optional::get);
    }

    @Override
    public Flux<ExperimentResponseTO> loadAll() {
        return from(this.connectionFactory.create())
                .flatMapMany(connection ->
                        from(connection.createStatement("select * from product_item").execute())
                                .flatMapMany(result -> result.map((row, metadata) -> new ExperimentMapper().map(row, metadata)))
                                .concatWith(from(connection.close()).cast(ExperimentEntity.class))
                )
                .map(ExperimentBinder.EXPERIMENT_BINDER::bind);
    }

    private Mono<Void> orchestrateDelta(XMLDeltaTO delta) {
        return this.orchestratorRepository.orchestrate(delta.getItems());
    }

    /**
     * (tries to) unmarshall(s) an InputStream to the desired object.
     *
     * @param xml {@link InputStream} an input stream with xml data content
     * @param <T> {@link T} Type of result to be return
     * @return {@link T} type of result to be return
     * @throws JAXBException If any error occur parsing the xml data
     */
    @SuppressWarnings("unchecked")
    public <T> Mono<T> unmarshallXml(final InputStream xml) throws JAXBException {
        return fromCallable(() -> (T) this.jaxb2Marshaller.unmarshal(new StreamSource(xml)));
    }
}
