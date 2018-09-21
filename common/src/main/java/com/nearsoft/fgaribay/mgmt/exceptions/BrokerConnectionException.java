package com.nearsoft.fgaribay.mgmt.exceptions;

public class BrokerConnectionException extends RuntimeException {
  public BrokerConnectionException() {}

  public BrokerConnectionException(String message) {
    super(message);
  }

  public BrokerConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public BrokerConnectionException(Throwable cause) {
    super(cause);
  }

  public BrokerConnectionException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
