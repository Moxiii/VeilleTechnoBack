package com.moxi.veilletechnoback.Config.Keycloak;


import com.moxi.veilletechnoback.DTO.AUTH.REGISTER.RegisterDTO;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;





@Service
public class KeycloakService {
@Autowired
private Keycloak adminKc;
@Value("${kc.server}") String server;
public AccessTokenResponse login(String username, String password) {
	try {
		return KeycloakBuilder.builder()
				.serverUrl(server)
				.realm("VeilleRealm")
				.clientId("front")
				.grantType(OAuth2Constants.PASSWORD)
				.username(username)
				.password(password)
				.build()
				.tokenManager()
				.grantToken();
	} catch (BadRequestException bre) {
		throw new IllegalStateException(
				"Échec de connexion : " + bre.getResponse().readEntity(String.class), bre);
	}
}

public String createUser(RegisterDTO dto){
	List<UserRepresentation> existingUsers = adminKc
			.realm("VeilleRealm")
			.users()
			.search(dto.getUsername(), true);

	if (!existingUsers.isEmpty()) {
		throw new RuntimeException("L'utilisateur existe déjà");
	}

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
	String userId = location.substring(location.lastIndexOf('/') + 1);
	UserRepresentation createdUser = adminKc.realm("VeilleRealm").users().get(userId).toRepresentation();
	createdUser.setEnabled(true);
	createdUser.setEmailVerified(true);
	createdUser.setRequiredActions(Collections.emptyList());
	return userId;
}

}
