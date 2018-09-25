package com.nearsoft.fgaribay.mgmt.model;

import java.io.Serializable;

public class ServiceRequest<T> implements Serializable {
  private String operation;
  private T data;

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

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
