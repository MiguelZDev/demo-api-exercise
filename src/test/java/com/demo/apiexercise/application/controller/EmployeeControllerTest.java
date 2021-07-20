package com.demo.apiexercise.application.controller;

import static com.demo.apiexercise.utils.UtilsForTest.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.demo.apiexercise.application.dto.person.PersonDto;
import com.demo.apiexercise.infrastructure.exception.ErrorHandler;
import com.demo.apiexercise.infrastructure.exception.custom.EmployeeNotFoundException;
import com.demo.apiexercise.infrastructure.exception.custom.PositionNotFoundException;
import com.demo.apiexercise.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

  private MockMvc mockMvc;

  @Mock
  private EmployeeService service;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(service))
      .setControllerAdvice(new ErrorHandler()).build();
  }

  @Test
  public void getAllByFilter_whenNoParams_okTest() throws Exception {
    // when
    when(service.filterBy(null, null)).thenReturn(Collections.singletonList(new EmployeeDto()));

    // verify
    mockMvc.perform(get("/employees")).andExpect(status().isOk());
  }

  @Test
  public void getAllByFilter_whenFullParams_okTest() throws Exception {
    // when
    when(service.filterBy(anyString(), anyString())).thenReturn(Collections.singletonList(new EmployeeDto()));

    // verify
    mockMvc.perform(get("/employees?name=Bob&position=dev")).andExpect(status().isOk());
  }

  @Test
  public void register_whenPositionExists_okTest() throws Exception {
    // given
    PersonDto personDto = new PersonDto();
    personDto.setName("Elliot");
    personDto.setLastName("Alderson");
    personDto.setAddress("217 East Broadway");
    personDto.setCellphone("987654321");
    personDto.setCityName("New York");
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setPositionName("dev");
    employeeDto.setPerson(personDto);
    employeeDto.setSalary(new BigDecimal(2000));

    // when
    when(service.register(any(EmployeeDto.class))).thenReturn(employeeDto);

    // verify
    mockMvc.perform(post("/employees")
      .content(asJsonString(employeeDto))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").exists())
      .andExpect(jsonPath("$.salary").exists())
      .andExpect(jsonPath("$.person.name").exists())
      .andExpect(jsonPath("$.positionName").exists());
  }

  @Test
  public void register_whenPositionNotFound_errorTest() throws Exception {
    // given
    PersonDto personDto = new PersonDto();
    personDto.setName("Elliot");
    personDto.setLastName("Alderson");
    personDto.setAddress("217 East Broadway");
    personDto.setCellphone("987654321");
    personDto.setCityName("New York");
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setPositionName("dev");
    employeeDto.setPerson(personDto);
    employeeDto.setSalary(new BigDecimal(2000));

    // when
    when(service.register(any(EmployeeDto.class))).thenThrow(new PositionNotFoundException("Job position not found"));

    // verify
    mockMvc.perform(post("/employees")
      .content(asJsonString(employeeDto))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(result -> assertTrue(result.getResolvedException() instanceof PositionNotFoundException))
      .andExpect(result ->
        assertEquals("Job position not found", result.getResolvedException().getMessage()));
  }

  @Test
  public void register_whenBadRequest_errorTest() throws Exception {
    // given
    EmployeeDto employeeDto = new EmployeeDto();

    // verify
    mockMvc.perform(post("/employees")
      .content(asJsonString(employeeDto))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void update_whenEmployeeExistsAndPositionExists_okTest() throws Exception {
    // given
    PersonDto personDto = new PersonDto();
    personDto.setId(1);
    personDto.setName("Elliot");
    personDto.setLastName("Alderson");
    personDto.setAddress("217 East Broadway");
    personDto.setCellphone("987654321");
    personDto.setCityName("New York");
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setId(1);
    employeeDto.setPositionName("dev");
    employeeDto.setPerson(personDto);
    employeeDto.setSalary(new BigDecimal(2000));

    // when
    when(service.update(anyInt(), any(EmployeeDto.class))).thenReturn(employeeDto);

    // verify
    mockMvc.perform(put("/employees/{employeeId}", 1)
      .content(asJsonString(employeeDto))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").exists())
      .andExpect(jsonPath("$.salary").exists())
      .andExpect(jsonPath("$.person.name").exists())
      .andExpect(jsonPath("$.positionName").exists());
  }

  @Test
  public void update_whenEmployeeNotFoundAndPositionExists_errorTest() throws Exception {
    // given
    PersonDto personDto = new PersonDto();
    personDto.setId(1);
    personDto.setName("Elliot");
    personDto.setLastName("Alderson");
    personDto.setAddress("217 East Broadway");
    personDto.setCellphone("987654321");
    personDto.setCityName("New York");
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setId(1);
    employeeDto.setPositionName("dev");
    employeeDto.setPerson(personDto);
    employeeDto.setSalary(new BigDecimal(2000));

    // when
    when(service.update(anyInt(), any(EmployeeDto.class))).thenThrow(new EmployeeNotFoundException("Employee not found"));

    // verify
    mockMvc.perform(put("/employees/{employeeId}", 1)
      .content(asJsonString(employeeDto))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
      .andExpect(result ->
        assertEquals("Employee not found", result.getResolvedException().getMessage()));
  }

  @Test
  public void update_whenEmployeeExistsAndPositionNotFound_errorTest() throws Exception {
    // given
    PersonDto personDto = new PersonDto();
    personDto.setId(1);
    personDto.setName("Elliot");
    personDto.setLastName("Alderson");
    personDto.setAddress("217 East Broadway");
    personDto.setCellphone("987654321");
    personDto.setCityName("New York");
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setId(1);
    employeeDto.setPositionName("dev");
    employeeDto.setPerson(personDto);
    employeeDto.setSalary(new BigDecimal(2000));

    // when
    when(service.update(anyInt(), any(EmployeeDto.class))).thenThrow(new PositionNotFoundException("Job position not found"));

    // verify
    mockMvc.perform(put("/employees/{employeeId}", 1)
      .content(asJsonString(employeeDto))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(result -> assertTrue(result.getResolvedException() instanceof PositionNotFoundException))
      .andExpect(result ->
        assertEquals("Job position not found", result.getResolvedException().getMessage()));
  }

  @Test
  public void update_whenBadRequest_errorTest() throws Exception {
    // given
    EmployeeDto employeeDto = new EmployeeDto();

    // verify
    mockMvc.perform(put("/employees/{employeeId}", 1)
      .content(asJsonString(employeeDto))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void delete_whenEmployeeExists_okTest() throws Exception {
    // when
    doNothing().when(service).delete(anyInt());

    // verify
    mockMvc.perform(delete("/employees/{employeeId}", 1)).andExpect(status().isOk());
  }

  @Test
  public void delete_whenEmployeeNotFound_errorTest() throws Exception {
    // when
    doThrow(new EmployeeNotFoundException("Employee not found")).when(service).delete(anyInt());

    // verify
    mockMvc.perform(delete("/employees/{employeeId}", 1))
      .andExpect(status().isNotFound())
      .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
      .andExpect(result ->
        assertEquals("Employee not found", result.getResolvedException().getMessage()));
  }

}
