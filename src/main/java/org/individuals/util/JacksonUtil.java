package org.individuals.util;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.Resources;

@Component
public class JacksonUtil {

  public <T> T read(String name, Class<T> clazz) {
    URL event = Resources.getResource(name);
    try (var fileReader = new FileReader(event.getPath())) {
      var objectMapper = new ObjectMapper();
      objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
      objectMapper.registerModule(new JavaTimeModule());
      return objectMapper.reader().readValue(fileReader, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
