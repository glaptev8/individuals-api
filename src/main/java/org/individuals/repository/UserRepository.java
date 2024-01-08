package org.individuals.repository;

import org.individuals.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Long> {
  Mono<Boolean> existsByEmail(String email);
}
