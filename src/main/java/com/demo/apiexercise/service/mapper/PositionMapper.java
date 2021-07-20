package com.demo.apiexercise.service.mapper;

import com.demo.apiexercise.application.dto.position.PositionDto;
import com.demo.apiexercise.infrastructure.common.GenericMapper;
import com.demo.apiexercise.persistence.entity.Position;

public interface PositionMapper extends GenericMapper<PositionDto, Position> {
}
