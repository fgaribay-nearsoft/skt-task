package com.nearsoft.fgaribay.mgmt.model;

import java.util.List;
import java.util.UUID;

public class ProductRequest {
    private UUID id = UUID.randomUUID();
    private String operation;
    private List<Product> products;

    public ProductRequest() {
    }

    public ProductRequest(String operation, List<Product> products) {
        this.operation = operation;
        this.products = products;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public UUID getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
