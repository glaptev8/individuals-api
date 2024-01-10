package org.individuals.service;

import org.leantech.individuals.dto.RegistrationRequest;
import org.leantech.individuals.dto.RegistrationResponseDto;

import reactor.core.publisher.Mono;

public interface UserService {
  Mono<RegistrationResponseDto> saveUser(RegistrationRequest registrationRequest);
}
