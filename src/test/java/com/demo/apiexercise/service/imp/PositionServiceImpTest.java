package com.demo.apiexercise.service.imp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.demo.apiexercise.application.dto.position.PositionDto;
import com.demo.apiexercise.persistence.entity.Employee;
import com.demo.apiexercise.persistence.entity.Position;
import com.demo.apiexercise.persistence.repository.EmployeeRepository;
import com.demo.apiexercise.persistence.repository.PositionRepository;
import com.demo.apiexercise.service.mapper.EmployeeMapper;
import com.demo.apiexercise.service.mapper.PositionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PositionServiceImpTest {

  private PositionServiceImp service;

  @Mock
  private PositionRepository positionRepository;
  @Mock
  private EmployeeRepository employeeRepository;
  @Mock
  private PositionMapper positionMapper;
  @Mock
  private EmployeeMapper employeeMapper;

  @BeforeEach
  public void setUp() {
    service = new PositionServiceImp(positionRepository, employeeRepository, positionMapper, employeeMapper);
  }

  @Test
  public void create_okTest() {
    // given
    Position mockPosition = mock(Position.class);

    // when
    when(positionMapper.toEntity(any(PositionDto.class))).thenReturn(mockPosition);
    when(positionRepository.save(any(Position.class))).thenReturn(mockPosition);
    when(positionMapper.toDTO(any(Position.class))).thenReturn(new PositionDto());

    // verify
    assertNotNull(service.create(new PositionDto()));
  }

  @Test
  public void getAll_okTest() {
    PositionDto positionDto = new PositionDto();
    positionDto.setName("dev");
    // when
    when(positionRepository.findAll()).thenReturn(Collections.singletonList(new Position()));
    when(positionMapper.toDTOs(anyList())).thenReturn(Collections.singletonList(positionDto));
    when(employeeRepository.filterByNameAndPositionIfPresent(isNull(), anyString()))
      .thenReturn(Collections.singletonList(new Employee()));
    when(employeeMapper.toDTOs(anyList())).thenReturn(Collections.singletonList(new EmployeeDto()));

    // verify
    List<PositionDto> result = service.getAll();
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

}
