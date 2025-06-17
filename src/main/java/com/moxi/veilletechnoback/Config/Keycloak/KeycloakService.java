package com.moxi.veilletechnoback.Config.Keycloak;


import com.moxi.veilletechnoback.DTO.AUTH.REGISTER.RegisterDTO;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class KeycloakService {

private final Keycloak adminKc;
public AccessTokenResponse login(String username, String password) {
	return KeycloakBuilder.builder()
			.serverUrl("http://auth.localhost")
			.realm("VeilleRealm")
			.clientId("front")
			.grantType(OAuth2Constants.PASSWORD)
			.username(username)
			.password(password)
			.build()
			.tokenManager()
			.getAccessToken();
}

public String createUser(RegisterDTO dto){
	UserRepresentation rep = new UserRepresentation();
	rep.setUsername(dto.getUsername());
	rep.setEmail(dto.getEmail());
	rep.setFirstName(dto.getFirstName());
	rep.setLastName(dto.getLastName());
	rep.setEnabled(true);

	CredentialRepresentation pwd = new CredentialRepresentation();
	pwd.setTemporary(false);
	pwd.setType(CredentialRepresentation.PASSWORD);
	pwd.setValue(dto.getPassword());
	rep.setCredentials(List.of(pwd));

	Response r = adminKc.realm("VeilleRealm").users().create(rep);
	if (r.getStatus() != 201) {
		throw new RuntimeException("Erreur KC : " + r.getStatus());
	}
	String location = r.getHeaderString("Location");
	return location.substring(location.lastIndexOf('/') + 1);
}

}
