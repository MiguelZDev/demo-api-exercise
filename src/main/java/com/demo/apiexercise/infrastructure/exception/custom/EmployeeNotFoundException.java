package com.demo.apiexercise.infrastructure.exception.custom;

public class EmployeeNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -5035893680936678231L;

  public EmployeeNotFoundException() {
    super();
  }

  public EmployeeNotFoundException(final String errorMessage, final Throwable cause) {
    super(errorMessage, cause);
  }

  public EmployeeNotFoundException(final String errorMessage) {
    super(errorMessage);
  }

  public EmployeeNotFoundException(final Throwable cause) {
    super(cause);
  }

}
