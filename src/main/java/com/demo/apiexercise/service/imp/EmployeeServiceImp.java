package com.demo.apiexercise.service.imp;

import static java.util.Objects.isNull;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.demo.apiexercise.infrastructure.exception.custom.EmployeeNotFoundException;
import com.demo.apiexercise.infrastructure.exception.custom.PositionNotFoundException;
import com.demo.apiexercise.persistence.entity.Employee;
import com.demo.apiexercise.persistence.entity.Position;
import com.demo.apiexercise.persistence.repository.EmployeeRepository;
import com.demo.apiexercise.persistence.repository.PositionRepository;
import com.demo.apiexercise.service.EmployeeService;
import com.demo.apiexercise.service.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final PositionRepository positionRepository;
  private final EmployeeMapper employeeMapper;

  @Override
  public List<EmployeeDto> filterBy(String name, String positionName) {
    return employeeRepository.filterByNameAndPositionIfPresent(name, positionName).stream().map(employeeMapper::toDTO)
      .collect(Collectors.toList());
  }

  @Override
  public EmployeeDto register(EmployeeDto request) {
    Employee employeeToRegister = employeeMapper.toEntity(request);
    Position position = findExistentPositionByName(request.getPositionName());
    employeeToRegister.setPosition(position);
    return employeeMapper.toDTO(employeeRepository.save(employeeToRegister));
  }

  private Position findExistentPositionByName(String positionName) {
    Position existentPosition = positionRepository.findByName(positionName);
    if (isNull(existentPosition)) {
      throw new PositionNotFoundException("Job position with name '"+ positionName + "' was not found");
    }
    return existentPosition;
  }

  @Override
  public EmployeeDto update(int employeeId, EmployeeDto request) {
    return employeeRepository.findById(employeeId).map(existentEmployee -> {
      Employee updatedEmployee = employeeMapper.toEntity(request);
      updatedEmployee.setId(existentEmployee.getId());
      Position position = findExistentPositionByName(request.getPositionName());
      updatedEmployee.setPosition(position);
      return employeeMapper.toDTO(employeeRepository.save(updatedEmployee));
    }).orElseThrow(() -> new EmployeeNotFoundException("Employee with id '" + employeeId + "' was not found"));
  }

  @Override
  public void delete(int employeeId) {
    Optional<Employee> existentEmployee = employeeRepository.findById(employeeId);
    if (existentEmployee.isPresent())
      employeeRepository.delete(existentEmployee.get());
    else
      throw new EmployeeNotFoundException("movie with id" + employeeId + " was not found");
  }
}
