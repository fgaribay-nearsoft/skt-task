package com.nearsoft.fgaribay.mgmt.model;

import java.util.List;
import java.util.UUID;

public class ProductResponse {

    private UUID id;
    private List<Product> products;
    private String operation;
    private String errorMessage;
    private boolean error;

    public ProductResponse(String operation, boolean error, String errorMessage, List<Product> products, UUID id) {
        this.operation = operation;
        this.error = error;
        this.errorMessage = errorMessage;
        this.products = products;
        this.id = id;
    }

    public ProductResponse() {
    }

    public ProductResponse(String operation, boolean error, String errorMessage, List<Product> products) {
        this.operation = operation;
        this.error = error;
        this.errorMessage = errorMessage;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
