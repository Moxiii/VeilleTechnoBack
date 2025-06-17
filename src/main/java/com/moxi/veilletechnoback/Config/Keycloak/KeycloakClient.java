package com.moxi.veilletechnoback.Config.Keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@Configuration
public class KeycloakClient {
@Bean
public Keycloak keycloakClient(){
	return KeycloakBuilder.builder().serverUrl("http://auth.localhost").realm("VeilleRealm").clientId("front").build();
}
}
