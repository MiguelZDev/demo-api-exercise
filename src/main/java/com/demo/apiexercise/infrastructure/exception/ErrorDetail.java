package com.demo.apiexercise.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {

  private final String errorCode;
  private final String errorMessage;
  private String timestamp;

}
