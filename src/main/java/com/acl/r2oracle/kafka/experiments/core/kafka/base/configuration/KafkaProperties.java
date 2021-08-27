package com.acl.r2oracle.kafka.experiments.core.kafka.base.configuration;

import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.serdes.CustomSerde;
import com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging.serdes.StandardSerde;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Properties class for Kafka configuration context.
 * @author Alejandro
 * @since 0.1.0
 */
@Configuration
@ConfigurationProperties("app.kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private String groupId;
    private String topicPrefix;
    private String enviromentPrefix;
    private List<String> queues;
    private String applicationId;
    private String baseDir;
    private Integer concurrency;
    private String compressionType;
    private String serializeClass;
    private String deserializeClass;

    public Properties getStreamProperties(String streamName) {
        Properties props = getDefaultStreamProperties(streamName);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, StandardSerde.class);
        return props;
    }

    public Properties getCustomStreamProperties(String streamName) {
        Properties props = getDefaultStreamProperties(streamName);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CustomSerde.class);
        return props;
    }

    public Properties getKafkaProperties() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 10000);
        props.put(AdminClientConfig.RETRIES_CONFIG, 10);
        return props;
    }

    //<editor-fold desc="Encapsulation">
    public String getBootstrapServers() {
        return bootstrapServers == null ? "127.0.0.1:9092" : bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTopicPrefix() {
        return topicPrefix;
    }

    public void setTopicPrefix(String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }

    public List<String> getQueues() {
        return queues == null ? Collections.emptyList() : queues;
    }

    public void setQueues(List<String> queues) {
        this.queues = queues;
    }

    public String getEnviromentPrefix() {
        return enviromentPrefix;
    }

    public void setEnviromentPrefix(String enviromentPrefix) {
        this.enviromentPrefix = enviromentPrefix;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public Integer getConcurrency() {
        return null == concurrency ? concurrency = 1 : concurrency;
    }

    public void setConcurrency(Integer concurrency) {
        this.concurrency = concurrency;
    }

    public String getCompressionType() {
        return compressionType == null ? "gzip" : compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public String getSerializeClass() {
        return serializeClass;
    }

    public void setSerializeClass(String serializeClass) {
        this.serializeClass = serializeClass;
    }

    public String getDeserializeClass() {
        return deserializeClass;
    }

    public void setDeserializeClass(String deserializeClass) {
        this.deserializeClass = deserializeClass;
    }

    private Properties getDefaultStreamProperties(String streamName) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, getApplicationId() + "-" + streamName);
        //directory to store the state
        props.put(StreamsConfig.STATE_DIR_CONFIG, getBaseDir() + "/" + streamName);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, getCompressionType());
        props.put("max.request.size", 16000000);
        return props;
    }
    //</editor-fold>
}
