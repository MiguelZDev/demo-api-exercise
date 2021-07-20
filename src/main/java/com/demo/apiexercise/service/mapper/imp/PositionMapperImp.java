package com.demo.apiexercise.service.mapper.imp;

import com.demo.apiexercise.application.dto.position.PositionDto;
import com.demo.apiexercise.persistence.entity.Position;
import com.demo.apiexercise.service.mapper.EmployeeMapper;
import com.demo.apiexercise.service.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionMapperImp implements PositionMapper {

  private final EmployeeMapper employeeMapper;

  @Override
  public Position toEntity(PositionDto dto) {
    Position position = new Position();
    position.setName(dto.getName());
    return position;
  }

  @Override
  public PositionDto toDTO(Position entity) {
    PositionDto response = new PositionDto();
    response.setId(entity.getId());
    response.setName(entity.getName());
    return response;
  }
}
