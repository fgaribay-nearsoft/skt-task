package com.nearsoft.fgaribay.mgmt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class ProductRequest implements Serializable {
    private UUID id;
    private String operation;
    private ArrayList<Product> products;

    public ProductRequest(UUID id, String operation, ArrayList<Product> products) {
        this.id = id;
        this.operation = operation;
        this.products = products;
    }

    public ProductRequest() {
    }

    public ProductRequest(String operation, ArrayList<Product> products) {
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

    public void setId(UUID id) {
        this.id = id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
