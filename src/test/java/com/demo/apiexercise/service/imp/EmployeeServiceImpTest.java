package com.demo.apiexercise.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.demo.apiexercise.infrastructure.exception.custom.EmployeeNotFoundException;
import com.demo.apiexercise.infrastructure.exception.custom.PositionNotFoundException;
import com.demo.apiexercise.persistence.entity.Employee;
import com.demo.apiexercise.persistence.entity.Position;
import com.demo.apiexercise.persistence.repository.EmployeeRepository;
import com.demo.apiexercise.persistence.repository.PositionRepository;
import com.demo.apiexercise.service.mapper.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImpTest {

  private EmployeeServiceImp service;

  @Mock
  private EmployeeRepository employeeRepository;
  @Mock
  private PositionRepository positionRepository;
  @Mock
  private EmployeeMapper employeeMapper;

  @BeforeEach
  public void setUp() {
    service = new EmployeeServiceImp(employeeRepository, positionRepository, employeeMapper);
  }

  @Test
  public void filterBy_okTest() {
    // when
    when(employeeRepository.filterByNameAndPositionIfPresent(anyString(), anyString()))
      .thenReturn(Collections.singletonList(new Employee()));
    when(employeeMapper.toDTO(any(Employee.class))).thenReturn(new EmployeeDto());

    // verify
    assertFalse(service.filterBy("test-name", "test-position").isEmpty());
  }

  @Test
  public void register_whenPositionExists_okTest() {
    // given
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setPositionName("dev");
    employeeDto.setSalary(new BigDecimal(2000));

    // when
    when(employeeMapper.toEntity(any(EmployeeDto.class))).thenReturn(new Employee());
    when(positionRepository.findByName(anyString())).thenReturn(new Position());
    when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());
    when(employeeMapper.toDTO(any(Employee.class))).thenReturn(employeeDto);

    // verify
    EmployeeDto result = service.register(employeeDto);
    assertNotNull(result);
    assertEquals("dev", result.getPositionName());
  }

  @Test
  public void register_whenPositionNotFound_errorTest() {
    // given
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setPositionName("dev");
    employeeDto.setSalary(new BigDecimal(2000));

    // when
    when(employeeMapper.toEntity(any(EmployeeDto.class))).thenReturn(new Employee());
    when(positionRepository.findByName(anyString())).thenReturn(null);

    // verify
    assertThrows(PositionNotFoundException.class, () -> service.register(employeeDto));
  }

  @Test
  public void updated_whenEmployeeExistsAndPositionExists_okTest() {
    // given
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setPositionName("dev");
    employeeDto.setSalary(new BigDecimal(2000));

    Employee employee = new Employee();
    employee.setId(1);

    // when
    when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
    when(employeeMapper.toEntity(any(EmployeeDto.class))).thenReturn(new Employee());
    when(positionRepository.findByName(anyString())).thenReturn(new Position());
    when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());
    when(employeeMapper.toDTO(any(Employee.class))).thenReturn(employeeDto);

    // verify
    EmployeeDto result = service.update(1, employeeDto);
    assertNotNull(result);
    assertEquals("dev", result.getPositionName());
  }

  @Test
  public void updated_whenEmployeeNotFound_errorTest() {
    // when
    when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

    // verify
    assertThrows(EmployeeNotFoundException.class, () -> service.update(1, new EmployeeDto()));
  }

  @Test
  public void updated_whenEmployeeExistsAndPositionNotFound_errorTest() {
    // given
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setPositionName("dev");
    employeeDto.setSalary(new BigDecimal(2000));

    Employee employee = new Employee();
    employee.setId(1);

    // when
    when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
    when(employeeMapper.toEntity(any(EmployeeDto.class))).thenReturn(new Employee());
    when(positionRepository.findByName(anyString())).thenReturn(null);

    // verify
    assertThrows(PositionNotFoundException.class, () -> service.update(1, employeeDto));
  }

  @Test
  public void delete_whenEmployeeExists_okTest() {
    // when
    when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(new Employee()));
    doNothing().when(employeeRepository).delete(any(Employee.class));

    // verify
    service.delete(1);
    verify(employeeRepository, times(1)).delete(any(Employee.class));
  }

  @Test
  public void delete_whenEmployeeNotFound_errorTest() {
    // when
    when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

    // verify
    assertThrows(EmployeeNotFoundException.class, () -> service.delete(1));
  }

}
