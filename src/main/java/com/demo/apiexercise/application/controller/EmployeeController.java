package com.demo.apiexercise.application.controller;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.demo.apiexercise.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService service;

  @GetMapping
  public ResponseEntity<List<EmployeeDto>> getAllByFilter(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String position) {
    return new ResponseEntity<>(service.filterBy(name, position), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<EmployeeDto> register(@Valid @RequestBody EmployeeDto request) {
    return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
  }

  @PutMapping("/{employeeId}")
  public ResponseEntity<EmployeeDto> update(@PathVariable int employeeId,
                                            @Valid @RequestBody EmployeeDto request) {
    return new ResponseEntity<>(service.update(employeeId, request), HttpStatus.OK);
  }

  @DeleteMapping("/{employeeId}")
  public ResponseEntity<Void> delete(@PathVariable int employeeId) {
    service.delete(employeeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
