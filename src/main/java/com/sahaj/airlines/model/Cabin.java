package com.sahaj.airlines.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Cabin {

  ECONOMY("Economy"), PREMIUM_ECONOMY("Premium Economy"), BUSINESS("Business"), FIRST("First");

  @JsonValue
  private final String desc;

  @JsonCreator
  public static Cabin getByDesc(String desc) {
    return Arrays.stream(Cabin.values())
        .filter(cabin -> cabin.getDesc().equals(desc.trim()))
        .findFirst()
        .orElse(null);
  }

  public static String getAllCabinCommaSeparated() {
    return Arrays.stream(Cabin.values()).map(Cabin::getDesc).collect(Collectors.joining(","));
  }

}
