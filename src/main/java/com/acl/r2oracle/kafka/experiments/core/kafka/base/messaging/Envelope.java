package com.acl.r2oracle.kafka.experiments.core.kafka.base.messaging;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class to wrap the messages passed/received through kafka system.
 *
 * @author Alejandro
 * @since 0.1.0
 */
public class Envelope<M> implements Serializable {
    private static final long serialVersionUID = -8200206179248206693L;
    private String id;
    private M message;
    private String topic;

    public Envelope() {
        this.id = UUID.randomUUID().toString();
    }

    public Envelope(M message, String topic) {
        this();
        this.message = message;
        this.topic = topic;
    }

    static public <T> Envelope<T> from(T message, String topic) {
        return new Envelope<>(message, topic);
    }

    static public <T> Envelope<T> from(String topic) {
        return new Envelope<>(null, topic);
    }

    //<editor-fold desc="Fluent Encapsulation">
    public Envelope<M> withMessage(M message) {
        this.setMessage(message);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Encapsulation">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public M getMessage() {
        return message;
    }

    public void setMessage(M message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    //</editor-fold>
}
