package com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.domain.QueueActionResponse;
import org.apache.kafka.common.KafkaFuture;

import java.util.List;

/**
 * Service for the administration of the queues
 *
 * @author Alejandro
 * @since 0.1.0
 */
public interface QueueManagerService {
    void init();

    KafkaFuture<List<String>> getQueues() throws Exception;

    QueueActionResponse createQueues(List<String> queueNames);

    QueueActionResponse createDefaultQueues();

    QueueActionResponse removeQueues(List<String> queueNames) throws Exception;
}
