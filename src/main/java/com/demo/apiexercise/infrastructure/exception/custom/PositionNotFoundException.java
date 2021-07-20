package com.demo.apiexercise.infrastructure.exception.custom;

public class PositionNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 5874627451772495019L;

  public PositionNotFoundException() {
    super();
  }

  public PositionNotFoundException(final String errorMessage, final Throwable cause) {
    super(errorMessage, cause);
  }

  public PositionNotFoundException(final String errorMessage) {
    super(errorMessage);
  }

  public PositionNotFoundException(final Throwable cause) {
    super(cause);
  }

}
