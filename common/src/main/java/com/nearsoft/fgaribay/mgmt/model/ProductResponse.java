package com.nearsoft.fgaribay.mgmt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class ProductResponse implements Serializable {

  private UUID id;
  private ArrayList<Product> products;
  private String operation;
  private String errorMessage;
  private boolean error;

  public ProductResponse(
      String operation, boolean error, String errorMessage, ArrayList<Product> products, UUID id) {
    this.operation = operation;
    this.error = error;
    this.errorMessage = errorMessage;
    this.products = products;
    this.id = id;
  }

  public ProductResponse() {}

  public ProductResponse(
      String operation, boolean error, String errorMessage, ArrayList<Product> products) {
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

  public ArrayList<Product> getProducts() {
    return products;
  }

  public void setProducts(ArrayList<Product> products) {
    this.products = products;
  }
}
