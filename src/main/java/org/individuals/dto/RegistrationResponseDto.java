package org.individuals.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponseDto {
  private String token;
  private long expiresIn;
  private long refreshExpiresIn;
  private String refreshToken;
  private String tokenType;
}
