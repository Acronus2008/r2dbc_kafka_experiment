package com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.serdes;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.Envelope;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.messages.StandardMessage;
import com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Default Kafka serializer.
 *
 * @since 0.1.0
 */
public class StandardSerializer implements Serializer<Envelope<StandardMessage>> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        //Do nothing!
    }

    @Override
    public byte[] serialize(String topic, Envelope<StandardMessage> envelope) {
        return JsonSerdes.jsonfyAsBytes(envelope.getMessage());
    }

    @Override
    public void close() {
        //Do nothing!
    }
}
