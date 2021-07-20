package com.demo.apiexercise.service.imp;

import com.demo.apiexercise.service.PositionService;
import com.demo.apiexercise.service.mapper.EmployeeMapper;
import com.demo.apiexercise.service.mapper.PositionMapper;
import com.demo.apiexercise.application.dto.position.PositionDto;
import com.demo.apiexercise.persistence.entity.Employee;
import com.demo.apiexercise.persistence.repository.EmployeeRepository;
import com.demo.apiexercise.persistence.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionServiceImp implements PositionService {

  private final PositionRepository positionRepository;
  private final EmployeeRepository employeeRepository;
  private final PositionMapper positionMapper;
  private final EmployeeMapper employeeMapper;

  @Override
  public PositionDto create(PositionDto request) {
    return positionMapper.toDTO(positionRepository.save(positionMapper.toEntity(request)));
  }

  @Override
  public List<PositionDto> getAll() {
    List<PositionDto> positions = positionMapper.toDTOs(positionRepository.findAll());
    return positions.stream().map(position -> {
      List<Employee> employees = employeeRepository.filterByNameAndPositionIfPresent(null, position.getName());
      position.setEmployees(employeeMapper.toDTOs(employees));
      return position;
    }).collect(Collectors.toList());
  }
}
