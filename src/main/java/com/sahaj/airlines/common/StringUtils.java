package com.sahaj.airlines.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@UtilityClass
@Slf4j
public class StringUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule());

  public static boolean isPatternMatching(String content, String regex) {
    if (!org.springframework.util.StringUtils.hasLength(content)) {
      return false;
    }

    return content.matches(regex);
  }

  public static <T> String toString(T t) {
    if (Objects.isNull(t)) {
      return null;
    }

    try {
      return objectMapper.writeValueAsString(t);
    } catch (JsonProcessingException ex) {
      log.error("Error while converting object {} to string", t.getClass().getName(), ex);
      throw new RuntimeException(ex);
    }
  }

}
