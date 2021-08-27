package com.acl.r2oracle.kafka.experiments.service.r2.contract.to;

import com.acl.r2oracle.kafka.experiments.service.r2.contract.enums.RedisResult;

public class RedisResultTO {
    private RedisResult result;
    private String message;

    public RedisResult getResult() {
        return result;
    }

    public void setResult(RedisResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RedisResultTO withResult(RedisResult result) {
        setResult(result);
        return this;
    }

    public RedisResultTO withMessage(String message) {
        setMessage(message);
        return this;
    }
}
