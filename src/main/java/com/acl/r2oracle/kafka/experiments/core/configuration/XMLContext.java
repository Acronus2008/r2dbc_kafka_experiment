package com.acl.r2oracle.kafka.experiments.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class XMLContext {
    private static final String[] xmlSchema = {
            "com.acl.r2oracle.kafka.experiments.service.xml.schema"
    };

    @Bean
    public Jaxb2Marshaller marshallerContactMember() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(String.join(":", xmlSchema));
        return marshaller;
    }
}
