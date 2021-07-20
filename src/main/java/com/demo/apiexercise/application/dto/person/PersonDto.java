package com.demo.apiexercise.application.dto.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDto {

  private int id;
  @NotBlank
  private String name;
  @NotBlank
  private String lastName;
  @NotBlank
  private String address;
  @NotBlank @Pattern(regexp = "\\d+", message = "el campo debe contener solo digitos")
  private String cellphone;
  @NotBlank
  private String cityName;

}
