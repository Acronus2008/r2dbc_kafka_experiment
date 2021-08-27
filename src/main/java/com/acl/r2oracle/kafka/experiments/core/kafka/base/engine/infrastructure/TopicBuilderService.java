package com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.infrastructure;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * Topics builder interface for streaming context.
 *
 * @author Alejandro
 * @since 0.1.0
 */
public interface TopicBuilderService {
    /**
     * Retrieves the configured topics prefix.
     *
     * @return The configured topics prefix.
     */
    String getPrefix();

    /**
     * Retrieves the configured environment.
     *
     * @return The configured environment.
     */
    String getEnviroment();

    /**
     * Build the proper topic for the given queue name.
     *
     * @param queueName The name of the queue to which the topic will be created.
     * @return The topic based on the specified queue name.
     */
    default String buildTopic(String queueName) {
        return format("%s-%s-%s", this.getPrefix(), this.getEnviroment(), queueName);
    }

    /**
     * Build the stream name for the given queue name.
     *
     * @param queueName The name of the queue to which the topic will be created.
     * @return The stream name based on the specified queue name.
     */
    default String buildFullStreamName(String queueName) {
        return format("%s-%s-%s", this.getPrefix(), this.getEnviroment(), queueName);
    }

    /**
     * Clean name of topic
     *
     * @param topicName name
     * @return final name
     */
    default String cleanTopic(String topicName) {
        String regex = format("%s-%s-", this.getPrefix(), this.getEnviroment()) + "(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(topicName);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
