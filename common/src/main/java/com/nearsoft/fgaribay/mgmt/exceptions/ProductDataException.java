package com.nearsoft.fgaribay.mgmt.exceptions;

public class ProductDataException extends RuntimeException {
  public ProductDataException() {}

  public ProductDataException(String message) {
    super(message);
  }

  public ProductDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductDataException(Throwable cause) {
    super(cause);
  }

  public ProductDataException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
