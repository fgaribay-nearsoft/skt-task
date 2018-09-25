package com.nearsoft.fgaribay.mgmt.exceptions;

import java.sql.SQLDataException;

public class ProductDataException extends SQLDataException {

  public ProductDataException(String message) {
    super(message);
  }

  public ProductDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductDataException(Throwable cause) {
    super(cause);
  }
}
