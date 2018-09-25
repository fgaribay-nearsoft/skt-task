package com.nearsoft.fgaribay.mgmt.model;

import java.io.Serializable;

public class ServiceResponse<T> implements Serializable {

  private T data;
  private String operation;
  private String errorMessage;
  private boolean error;

  public ServiceResponse() {}

  public ServiceResponse(String operation, boolean error, String errorMessage, T data) {
    this.operation = operation;
    this.error = error;
    this.errorMessage = errorMessage;
    this.data = data;
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

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
