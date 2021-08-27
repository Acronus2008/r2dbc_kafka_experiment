package com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.serdes;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomDeserializer implements Deserializer<Envelope<Map>> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Envelope<Map> deserialize(String topic, byte[] bytes) {
        return Envelope.<Map>from(topic)
                .withMessage(JsonSerdes.parse(bytes, Map.class));
    }

    @Override
    public void close() {

    }
}
