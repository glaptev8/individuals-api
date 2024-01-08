package org.individuals.service;

import org.individuals.dto.RegistrationRequest;
import org.individuals.dto.RegistrationResponseDto;
import org.individuals.dto.UserDto;

import reactor.core.publisher.Mono;

public interface KeycloakAdminClientService {

  Mono<RegistrationResponseDto> createUser(RegistrationRequest registrationRequest);

  Mono<RegistrationResponseDto> getToken(RegistrationRequest registrationRequest);

  Mono<UserDto> getUser();
}
