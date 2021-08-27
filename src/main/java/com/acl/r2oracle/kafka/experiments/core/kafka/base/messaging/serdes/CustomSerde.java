package com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.serdes;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Custom Serializer-DesSerializer for {@link Map}
 *
 * @since 0.1.0
 */
public class CustomSerde implements Serde<Envelope<Map>> {
    final private CustomSerializer serializer = new CustomSerializer();
    final private CustomDeserializer deserializer = new CustomDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        serializer.configure(configs, isKey);
        deserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer<Envelope<Map>> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<Envelope<Map>> deserializer() {
        return deserializer;
    }
}
