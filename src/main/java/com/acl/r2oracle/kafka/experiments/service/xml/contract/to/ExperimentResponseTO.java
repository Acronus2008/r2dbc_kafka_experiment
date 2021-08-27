package com.acl.r2oracle.kafka.experiments.service.xml.contract.to;

import com.acl.r2oracle.kafka.experiments.core.TransferObject;

public class ExperimentResponseTO extends TransferObject {
    private String id;
    private String date;
    private String productId;
    private int stock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
