package org.individuals.service;

import org.individuals.util.JacksonUtil;
import org.leantech.individuals.dto.RegistrationRequest;
import org.leantech.individuals.dto.RegistrationResponseDto;
import org.leantech.person.dto.UserSaveDto;
import org.leantech.webclient.client.person.PersonClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final KeycloakAdminClientServiceImpl keycloakAdminClientService;
  private final PersonClient personClient;
  private final JacksonUtil jacksonUtil;

  @Override
  public Mono<RegistrationResponseDto> saveUser(RegistrationRequest registrationRequest) {
    if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
      return Mono.error(new RuntimeException("confirm password incorrect"));
    }

    return keycloakAdminClientService.createUser(registrationRequest)
      .flatMap(registrationResponseDto -> {
        var userSaveDto = jacksonUtil.read("drafts/test_user.json", UserSaveDto.class);
        userSaveDto.getUser().setPassword(registrationRequest.getPassword());
        userSaveDto.getUser().setEmail(registrationRequest.getEmail());
        return personClient.save(userSaveDto)
          .map(user -> registrationResponseDto);
      });
  }
}
