package org.individuals.service;

import org.individuals.dto.RegistrationRequest;
import org.individuals.dto.RegistrationResponseDto;
import org.individuals.entity.User;

import reactor.core.publisher.Mono;

public interface UserService {
  Mono<RegistrationResponseDto> saveUser(RegistrationRequest registrationRequest);
}
