package com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.serdes;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Custom Serializer for {@link Map}
 *
 * @since 0.1.0
 */
public class CustomSerializer implements Serializer<Envelope<Map>> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Envelope<Map> envelope) {
        return JsonSerdes.jsonfyAsBytes(envelope.getMessage());
    }

    @Override
    public void close() {

    }
}
