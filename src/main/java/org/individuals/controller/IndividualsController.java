package org.individuals.controller;

import org.individuals.dto.RegistrationRequest;
import org.individuals.dto.RegistrationResponseDto;
import org.individuals.dto.UserDto;
import org.individuals.service.KeycloakAdminClientService;
import org.individuals.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class IndividualsController {
  private final KeycloakAdminClientService keycloakAdminClientService;
  private final UserService userService;

  @PostMapping("/registration")
  public Mono<RegistrationResponseDto> register(@RequestBody RegistrationRequest request) {
    return userService.saveUser(request);
  }

  @GetMapping("/userinfo")
  public Mono<UserDto> userInfo() {
    return keycloakAdminClientService.getUser();
  }

  @PostMapping("/getToken")
  public Mono<RegistrationResponseDto> getToken(@RequestBody RegistrationRequest request) {
    return keycloakAdminClientService.getToken(request);
  }
}
