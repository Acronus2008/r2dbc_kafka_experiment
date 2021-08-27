package com.acl.r2oracle.kafka.experiments.core.kafka.base.engine.domain;

import org.apache.kafka.common.KafkaFuture;

import java.util.HashMap;
import java.util.Map;

public class QueueActionResponse {
    private Map<String, KafkaFuture<Void>> queuesResult;

    public QueueActionResponse(Map<String, KafkaFuture<Void>> queuesResult) {
        this.queuesResult = queuesResult;
    }

    //<editor-fold desc="Encapsulation">
    public Map<String, KafkaFuture<Void>> getQueuesResult() {
        return null != queuesResult ? queuesResult = new HashMap<>() : queuesResult;
    }

    public void setQueuesResult(Map<String, KafkaFuture<Void>> queuesResult) {
        this.queuesResult = queuesResult;
    }
    //</editor-fold>
}
