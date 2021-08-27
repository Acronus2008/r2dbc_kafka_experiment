package com.acl.r2oracle.kafka.experiments.core.kafka.reactive;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.configuration.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

/**
 * Kafka utility class.
 *
 * @author Alejandro
 * @since 0.1.0
 */
public class KafkaUtil {
    private static final String serializeBasePackage = "com.cencosud.puntos.r2oracle.core.kafka.base.messaging.serdes.%s";

    private KafkaUtil() {
        throw new AssertionError("No 'KafkaUtil' instances for you!");
    }

    static public Properties getSenderProperties(KafkaProperties properties) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, properties.getApplicationId() + randomUUID().toString());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getSerializeClass(properties.getSerializeClass()));
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 163840);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, properties.getCompressionType());
        return props;
    }

    static public Properties getReceiverProperties(KafkaProperties properties) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getDeserializeClass(properties.getDeserializeClass()));
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 16000000);
        return props;
    }

    private static String getSerializeClass(String serializeClass) {
        return null == serializeClass || serializeClass.isEmpty() || serializeClass.trim().equals("") ? format(serializeBasePackage, "StandardSerializer") : format(serializeBasePackage, serializeClass);
    }

    private static String getDeserializeClass(String deSerializeClass) {
        return null == deSerializeClass || deSerializeClass.isEmpty() || deSerializeClass.trim().equals("") ? format(serializeBasePackage, "StandardDeserializer") : format(serializeBasePackage, deSerializeClass);
    }
}
