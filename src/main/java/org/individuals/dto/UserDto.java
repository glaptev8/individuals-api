package org.individuals.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private String email;
}
