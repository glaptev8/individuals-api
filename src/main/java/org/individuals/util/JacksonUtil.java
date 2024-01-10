package org.individuals.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class JacksonUtil {

  public <T> T read(String name, Class<T> clazz) {
    try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(name)) {
      if (resourceStream == null) {
        throw new FileNotFoundException("file not found: " + name);
      }

      var objectMapper = new ObjectMapper();
      objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
      objectMapper.registerModule(new JavaTimeModule());

      return objectMapper.readValue(resourceStream, clazz);
    } catch (IOException e) {
      throw new RuntimeException("reading file exception: " + name, e);
    }
  }

}
