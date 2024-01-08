package org.individuals.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("users")
public class User {
  @Id
  private Long id;
  private String email;
}
