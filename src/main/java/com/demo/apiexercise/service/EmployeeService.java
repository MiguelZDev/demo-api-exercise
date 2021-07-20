package com.demo.apiexercise.service;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {

  List<EmployeeDto> filterBy(String name, String positionName);
  EmployeeDto register(EmployeeDto request);
  EmployeeDto update(int employeeId, EmployeeDto request);
  void delete(int employeeId);

}
