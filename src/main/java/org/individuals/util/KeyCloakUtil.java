package org.individuals.util;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class KeyCloakUtil {

  @Value("${keycloak.auth-server-url}")
  private String serverUrl;
  @Value("${keycloak.realm}")
  private String realm;
  @Value("${keycloak.secret}")
  private String secret;
  @Value("${keycloak.scope}")
  private String scope;
  @Value("${keycloak.client-id}")
  private String clientId;

  public Keycloak getKeyCloakForClient() {
    return KeycloakBuilder.builder()
      .serverUrl(serverUrl)
      .realm(realm)
      .clientSecret(secret)
      .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
      .clientId(clientId)
      .build();
  }

  public Keycloak getKeyCloakByUserNameAndPassword(String username, String password) {
    return KeycloakBuilder.builder()
      .serverUrl(serverUrl)
      .realm(realm)
      .scope(scope)
      .clientSecret(secret)
      .grantType(OAuth2Constants.PASSWORD)
      .username(username)
      .password(password)
      .clientId(clientId)
      .build();
  }
}
