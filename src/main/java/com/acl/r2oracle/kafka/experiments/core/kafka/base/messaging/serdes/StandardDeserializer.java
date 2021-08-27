package com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.serdes;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.messages.StandardMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;


/**
 * Default Kafka deserializer.
 *
 * @since 0.1.0
 */
public class StandardDeserializer implements Deserializer<Envelope<StandardMessage>> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        //Do nothing!
    }

    @Override
    public Envelope<StandardMessage> deserialize(String topic, byte[] bytes) {
        return Envelope.<StandardMessage>from(topic)
                .withMessage(JsonSerdes.parse(bytes, StandardMessage.class));
    }

    @Override
    public void close() {
        //Do nothing!
    }
}
