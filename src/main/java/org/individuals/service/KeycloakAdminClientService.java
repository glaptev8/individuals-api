package org.individuals.service;

import org.leantech.individuals.dto.RegistrationRequest;
import org.leantech.individuals.dto.RegistrationResponseDto;
import org.leantech.person.dto.UserDto;

import reactor.core.publisher.Mono;

public interface KeycloakAdminClientService {

  Mono<RegistrationResponseDto> createUser(RegistrationRequest registrationRequest);

  Mono<RegistrationResponseDto> getToken(RegistrationRequest registrationRequest);

  Mono<UserDto> getUser();
}
