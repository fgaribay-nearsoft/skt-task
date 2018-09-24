package com.nearsoft.fgaribay.mgmt.model;

import java.io.Serializable;
import java.util.UUID;

public class ServiceRequest<T> implements Serializable {
  private UUID id;
  private String operation;
  private T data;

  public ServiceRequest(UUID id, String operation, T data) {
    this.id = id;
    this.operation = operation;
    this.data = data;
  }

  public ServiceRequest() {}

  public ServiceRequest(String operation, T data) {
    this.operation = operation;
    this.data = data;
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

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
