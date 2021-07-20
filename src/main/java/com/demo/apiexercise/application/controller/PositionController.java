package com.demo.apiexercise.application.controller;

import com.demo.apiexercise.service.PositionService;
import com.demo.apiexercise.application.dto.position.PositionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

  private final PositionService service;

  @GetMapping
  public ResponseEntity<List<PositionDto>> getAll() {
    return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PositionDto> create(@Valid @RequestBody PositionDto request) {
    return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
  }

}
