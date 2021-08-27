package com.acl.r2oracle.kafka.experiments.service.xml.contract.to;

import com.acl.r2oracle.kafka.experiments.core.TransferObject;

public class ItemTO extends TransferObject {
    private String date;
    private String productId;
    private Integer stock;

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
