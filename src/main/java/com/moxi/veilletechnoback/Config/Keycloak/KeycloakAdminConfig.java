package com.moxi.veilletechnoback.Config.Keycloak;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {
@Bean
Keycloak adminKc(
		@Value("${kc.server}") String server,
		@Value("${kc.admin.user}") String admin,
		@Value("${kc.admin.password}") String pass) {

	return KeycloakBuilder.builder()
			.serverUrl(server)
			.realm("master")
			.clientId("admin-cli")
			.username(admin)
			.password(pass)
			.grantType(OAuth2Constants.PASSWORD)
			.build();
}
}
