package org.individuals.service;

import org.individuals.dto.RegistrationRequest;
import org.individuals.dto.RegistrationResponseDto;
import org.individuals.entity.User;
import org.individuals.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final KeycloakAdminClientServiceImpl keycloakAdminClientService;
  private final UserRepository userRepository;

  @Override
  public Mono<RegistrationResponseDto> saveUser(RegistrationRequest registrationRequest) {
    if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
      return Mono.error(new RuntimeException("confirm password incorrect"));
    }

    return userRepository.existsByEmail(registrationRequest.getEmail())
      .flatMap(userExists -> {
        if (userExists) {
          return Mono.error(new RuntimeException("user exists"));
        }
        return keycloakAdminClientService.createUser(registrationRequest)
          .flatMap(registrationResponseDto -> userRepository.save(User.builder()
                                                                    .email(registrationRequest.getEmail())
                                                                    .build())
            .map(user -> registrationResponseDto));
      });
  }
}
