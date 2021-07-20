package com.demo.apiexercise.application.controller;

import static com.demo.apiexercise.utils.UtilsForTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.apiexercise.application.dto.employee.EmployeeDto;
import com.demo.apiexercise.application.dto.position.PositionDto;
import com.demo.apiexercise.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class PositionControllerTest {

  private MockMvc mockMvc;

  @Mock
  private PositionService positionService;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new PositionController(positionService)).build();
  }

  @Test
  public void getAll_okTest() throws Exception {
    //given
    PositionDto request = new PositionDto();
    request.setName("dev");

    PositionDto response = new PositionDto();
    response.setId(1);
    response.setName("dev");
    response.setEmployees(Collections.singletonList(new EmployeeDto()));

    // when
    when(positionService.getAll()).thenReturn(Collections.singletonList(response));

    // verify
    mockMvc.perform(get("/positions"))
      .andExpect(status().isOk());
  }

  @Test
  public void create_okTest() throws Exception {
    //given
    PositionDto request = new PositionDto();
    request.setName("dev");

    PositionDto response = new PositionDto();
    response.setId(1);
    response.setName("dev");

    // when
    when(positionService.create(any(PositionDto.class))).thenReturn(response);

    // verify
    mockMvc.perform(post("/positions")
      .content(asJsonString(request))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").exists())
      .andExpect(jsonPath("$.name").exists());
  }

}
