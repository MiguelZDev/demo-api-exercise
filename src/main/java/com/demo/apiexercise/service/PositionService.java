package com.demo.apiexercise.service;

import com.demo.apiexercise.application.dto.position.PositionDto;

import java.util.List;

public interface PositionService {

  PositionDto create(PositionDto request);
  List<PositionDto> getAll();

}
