package com.sahaj.airlines.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static java.nio.file.Files.*;

@UtilityClass
@Slf4j
public class FileUtils {

  public static <T> T readData(ObjectMapper objectMapper, String path, TypeReference<T> type) {
    try {
      return objectMapper.readValue(readString(Path.of(path)), type);
    } catch (IOException ex) {
      log.error("Error while parsing file at path = {}", path, ex);
      throw new RuntimeException(ex);
    }
  }

  public static <T> void write(String path, T t) {
    try {
      Files.writeString(Path.of(path), t.toString());
    } catch (IOException ex) {
      log.error("Error while writing to a file at path = {}", path, ex);
      throw new RuntimeException(ex);
    }
  }

}
