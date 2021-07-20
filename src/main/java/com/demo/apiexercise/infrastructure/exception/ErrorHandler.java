package com.demo.apiexercise.infrastructure.exception;

import com.demo.apiexercise.infrastructure.exception.custom.EmployeeNotFoundException;
import com.demo.apiexercise.infrastructure.exception.custom.PositionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(PositionNotFoundException.class)
  protected ResponseEntity<ErrorDetail> positionNotFound(final PositionNotFoundException ex) {
    ErrorDetail errorDetail = ErrorDetail.builder()
      .errorCode("POS-01")
      .errorMessage(ex.getMessage())
      .timestamp(LocalDateTime.now(ZoneId.of("UTC")).toString())
      .build();
    log.error("[ErrorHandler:positionNotFound] ErrorDetail: {}", errorDetail);
    return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(EmployeeNotFoundException.class)
  protected ResponseEntity<ErrorDetail> employeeNotFound(final EmployeeNotFoundException ex) {
    ErrorDetail errorDetail = ErrorDetail.builder()
      .errorCode("EMP-01")
      .errorMessage(ex.getMessage())
      .timestamp(LocalDateTime.now(ZoneId.of("UTC")).toString())
      .build();
    log.error("[ErrorHandler:employeeNotFound] ErrorDetail: {}", errorDetail);
    return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
  }

  @NonNull
  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex,
                                                       HttpHeaders headers,
                                                       HttpStatus status,
                                                       WebRequest request) {
    Map<String, Set<String>> errorsMap =  manageValidationError(ex.getBindingResult().getFieldErrors());
    return new ResponseEntity<>(errorsMap.isEmpty()? ex:errorsMap, headers, status);
  }

  @NonNull
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    final BindingResult bindingResult = ex.getBindingResult();
    List<ErrorDetail> errorList = new ArrayList<>(1);
    if (bindingResult.hasErrors()) {
      final List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
      for (FieldError fieldError : fieldErrorList) {
        errorList.add(ErrorDetail.builder().errorMessage(fieldError.getField()+": "+fieldError.getDefaultMessage())
          .timestamp(LocalDateTime.now(ZoneId.of("UTC")).toString()).build());
      }
    }
    return new ResponseEntity<>(errorList, headers, status);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstrainViolations(final ConstraintViolationException ex) {
    List<ErrorDetail> errors = ex.getConstraintViolations().stream()
      .map(exC -> ErrorDetail.builder().errorMessage(exC.getMessageTemplate())
        .timestamp(LocalDateTime.now(ZoneId.of("UTC")).toString()).build())
      .collect(Collectors.toList());
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  private Map<String, Set<String>> manageValidationError(List<FieldError> fieldErrors) {
    return fieldErrors.stream().collect(
      Collectors.groupingBy(FieldError::getField,
        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));
  }

}
