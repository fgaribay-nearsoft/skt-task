package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.exceptions.BrokerConnectionException;
import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ManagementControllerExceptionHandler {
  @ExceptionHandler(ProductDataException.class)
  public ResponseEntity handleProductDataException(ProductDataException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler(BrokerConnectionException.class)
  public ResponseEntity handleBrokerConnectionException(ProductDataException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }
}
