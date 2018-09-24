package com.nearsoft.fgaribay.mgmt.model;

import java.io.Serializable;
import java.util.UUID;

public class ServiceResponse<T> implements Serializable {

  private UUID id;
  private T data;
  private String operation;
  private String errorMessage;
  private boolean error;

  public ServiceResponse(String operation, boolean error, String errorMessage, T data, UUID id) {
    this.operation = operation;
    this.error = error;
    this.errorMessage = errorMessage;
    this.data = data;
    this.id = id;
  }

  public ServiceResponse() {}

  public ServiceResponse(String operation, boolean error, String errorMessage, T data) {
    this.operation = operation;
    this.error = error;
    this.errorMessage = errorMessage;
    this.data = data;
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

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
