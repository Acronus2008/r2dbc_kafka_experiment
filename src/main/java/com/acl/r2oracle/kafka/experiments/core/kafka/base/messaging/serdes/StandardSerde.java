package com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.serdes;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.messages.StandardMessage;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Serializer-Deserializer for {@link StandardMessage}.
 *
 * @since 0.1.0
 */
public class StandardSerde implements Serde<Envelope<StandardMessage>> {
    private final StandardSerializer serializer = new StandardSerializer();
    private final StandardDeserializer deserializer = new StandardDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.serializer.configure(configs, isKey);
        this.deserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        this.serializer.close();
        this.deserializer.close();
    }

    @Override
    public Serializer<Envelope<StandardMessage>> serializer() {
        return this.serializer;
    }

    @Override
    public Deserializer<Envelope<StandardMessage>> deserializer() {
        return this.deserializer;
    }
}
