package com.nearsoft.fgaribay.mgmt.exceptions;

public class UnresponsiveBrokerException extends CommunicationFailureException {

  public UnresponsiveBrokerException(String message) {
    super(message);
  }
}
