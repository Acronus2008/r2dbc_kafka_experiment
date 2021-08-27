package com.acl.r2oracle.kafka.experiments.core;

import com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes;

import java.io.Serializable;

import static com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes.jsonfy;

public class TransferObject implements Serializable {
    @Override
    public String toString() {
        return JsonSerdes.jsonfy(this);
    }
}
