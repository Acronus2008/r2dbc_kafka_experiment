package com.acl.r2oracle.kafka.experiments.service.r2.contract.to;

public class HealthCheckTO {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static HealthCheckTO withResult(String result) {
        HealthCheckTO r = new HealthCheckTO();
        r.setResult(result);
        return r;
    }
}
