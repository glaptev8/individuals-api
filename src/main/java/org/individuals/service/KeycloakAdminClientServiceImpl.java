package org.individuals.service;

import java.util.List;

import org.individuals.util.KeyCloakUtil;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.leantech.individuals.dto.RegistrationRequest;
import org.leantech.individuals.dto.RegistrationResponseDto;
import org.leantech.person.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAdminClientServiceImpl implements KeycloakAdminClientService {

  private final KeyCloakUtil keycloak;
  @Value("${keycloak.realm}")
  private String realm;

  @Override
  public Mono<RegistrationResponseDto> createUser(RegistrationRequest registrationRequest) {
    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(registrationRequest.getPassword());
    credential.setTemporary(false);

    UserRepresentation user = new UserRepresentation();
    user.setUsername(registrationRequest.getEmail());
    user.setEmail(registrationRequest.getEmail());
    user.setEnabled(true);
    user.setCredentials(List.of(credential));

    UsersResource usersResource = keycloak.getKeyCloakForClient().realm(realm).users();

    try (Response response = usersResource.create(user)) {
      if (response.getStatus() == Status.CREATED.getStatusCode()) {
        return getToken(registrationRequest);
      }
      else {
        log.info("user {} was not created", registrationRequest);
      }
    }

    return Mono.empty();
  }



  @Override
  public Mono<RegistrationResponseDto> getToken(RegistrationRequest registrationRequest) {
    var accessToken = keycloak
      .getKeyCloakByUserNameAndPassword(registrationRequest.getEmail(), registrationRequest.getPassword())
      .tokenManager()
      .getAccessToken();

    var build = RegistrationResponseDto.builder()
      .token(accessToken.getToken())
      .expiresIn(accessToken.getExpiresIn())
      .tokenType(accessToken.getTokenType())
      .refreshExpiresIn(accessToken.getRefreshExpiresIn())
      .refreshToken(accessToken.getRefreshToken())
      .build();

    return Mono.just(build);
  }

  @Override
  public Mono<UserDto> getUser() {
    return ReactiveSecurityContextHolder.getContext()
      .map(context -> (Jwt) context.getAuthentication().getPrincipal())
      .map(jwt -> {
        var userDto = new UserDto();
        userDto.setEmail(jwt.getClaimAsString("email"));
        return userDto;
      });
  }
}
