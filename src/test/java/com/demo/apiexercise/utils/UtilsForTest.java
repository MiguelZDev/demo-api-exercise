package com.demo.apiexercise.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilsForTest {

  private UtilsForTest() { }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
