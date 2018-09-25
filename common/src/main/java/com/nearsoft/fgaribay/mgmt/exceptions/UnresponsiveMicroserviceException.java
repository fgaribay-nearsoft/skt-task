package com.nearsoft.fgaribay.mgmt.exceptions;

public class UnresponsiveMicroserviceException extends CommunicationFailureException {

  public UnresponsiveMicroserviceException(String message) {
    super(message);
  }
}
