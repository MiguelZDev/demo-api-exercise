package com.demo.apiexercise.service.mapper.imp;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.demo.apiexercise.persistence.entity.Employee;
import com.demo.apiexercise.service.mapper.EmployeeMapper;
import com.demo.apiexercise.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapperImp implements EmployeeMapper {

  private final PersonMapper personMapper;

  @Override
  public Employee toEntity(EmployeeDto dto) {
    Employee employee = new Employee();
    employee.setSalary(dto.getSalary());
    employee.setPerson(personMapper.toEntity(dto.getPerson()));
    return employee;
  }

  @Override
  public EmployeeDto toDTO(Employee entity) {
    EmployeeDto response = new EmployeeDto();
    response.setId(entity.getId());
    response.setSalary(entity.getSalary());
    response.setPerson(personMapper.toDTO(entity.getPerson()));
//    response.setPositionName(entity.getPosition().getName());
    return response;
  }
}
