package com.nearsoft.fgaribay.mgmt.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class ProductRequest implements Serializable {
  private UUID id;
  private String operation;
  private List<Product> products;

  public ProductRequest(UUID id, String operation, List<Product> products) {
    this.id = id;
    this.operation = operation;
    this.products = products;
  }

  public ProductRequest() {}

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

  public void setId(UUID id) {
    this.id = id;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }
}
