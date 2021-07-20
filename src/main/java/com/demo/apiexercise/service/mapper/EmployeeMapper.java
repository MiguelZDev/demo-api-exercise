package com.demo.apiexercise.service.mapper;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.demo.apiexercise.infrastructure.common.GenericMapper;
import com.demo.apiexercise.persistence.entity.Employee;

public interface EmployeeMapper extends GenericMapper<EmployeeDto, Employee> {
}
