package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveBrokerException;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveMicroserviceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ManagementControllerExceptionHandler {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(ManagementControllerExceptionHandler.class);

  @ExceptionHandler(ProductDataException.class)
  public ResponseEntity handleProductDataException(ProductDataException e) {
    LOGGER.error(
        "Exception caught in the product management controller: The database issued an error.", e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler(UnresponsiveMicroserviceException.class)
  public ResponseEntity handleUnresponsiveMicroserviceException(
      UnresponsiveMicroserviceException e) {
    LOGGER.error(
        "Exception caught in the product management controller: The microservice is unresponsive.",
        e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }

  @ExceptionHandler(UnresponsiveBrokerException.class)
  public ResponseEntity handleUnresponsiveBrokerException(UnresponsiveMicroserviceException e) {
    LOGGER.error(
        "Exception caught in the product management controller: The broker is unresponsive.", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }
}
