package com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.impl;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.QueueManagerService;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure.TopicBuilderService;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.configuration.KafkaProperties;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.domain.QueueActionResponse;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.kafka.clients.admin.AdminClient.create;
import static org.slf4j.LoggerFactory.getLogger;

public class KafkaQueueManagerServiceImpl implements QueueManagerService {
    private static final Logger logger = getLogger(KafkaQueueManagerServiceImpl.class);
    private final KafkaProperties properties;
    private final TopicBuilderService topicBuilderService;
    private AdminClient kafkaAdminClient;

    public KafkaQueueManagerServiceImpl(KafkaProperties properties, TopicBuilderService topicBuilderService) {
        this.properties = properties;
        this.topicBuilderService = topicBuilderService;
    }

    @Override
    public void init() {
        logger.info("Initializing kafka service");
        this.kafkaAdminClient = create(this.properties.getKafkaProperties());
        this.createDefaultQueues();
    }

    @Override
    public QueueActionResponse createDefaultQueues() {
        return this.createQueues(properties.getQueues());
    }

    @Override
    public KafkaFuture<List<String>> getQueues() {
        return kafkaAdminClient.listTopics().names()
                .thenApply(rawNames -> rawNames.stream().map(topicBuilderService::cleanTopic).collect(toList()));
    }

    @Override
    public QueueActionResponse createQueues(List<String> rawTopics) {
        List<NewTopic> topics = rawTopics.stream()
                .map(queueName -> new NewTopic(topicBuilderService.buildTopic(queueName), 1, (short) 1))
                .collect(toList());
        CreateTopicsResult result = kafkaAdminClient.createTopics(topics);
        return new QueueActionResponse(result.values());
    }

    @Override
    public QueueActionResponse removeQueues(List<String> rawTopics) {
        List<String> topics = rawTopics.stream()
                .map(topicBuilderService::buildTopic)
                .collect(toList());
        DeleteTopicsResult result = kafkaAdminClient.deleteTopics(topics);
        return new QueueActionResponse(result.values());
    }
}
