package com.demo.apiexercise.application.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.demo.apiexercise.application.dto.person.PersonDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

  private int id;
  @NotNull @DecimalMin("0.0")
  private BigDecimal salary;
  @NotNull
  private @Valid PersonDto person;
  @NotBlank
  private String positionName;

}
