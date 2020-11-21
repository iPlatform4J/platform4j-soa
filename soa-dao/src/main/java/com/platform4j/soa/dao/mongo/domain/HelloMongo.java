package com.platform4j.soa.dao.mongo.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(
        collection = "mongoorder"
)
public class HelloMongo {
    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
