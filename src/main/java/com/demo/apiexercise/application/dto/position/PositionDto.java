package com.demo.apiexercise.application.dto.position;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionDto {

  private int id;
  @NotBlank
  private String name;
  private List<EmployeeDto> employees;

}
