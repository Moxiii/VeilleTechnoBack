package com.moxi.veilletechnoback.Controller;


import com.moxi.veilletechnoback.Config.Keycloak.KeycloakService;
import com.moxi.veilletechnoback.DTO.AUTH.Login.LoginDTO;
import com.moxi.veilletechnoback.DTO.AUTH.REGISTER.RegisterDTO;
import com.moxi.veilletechnoback.User.UserService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import java.time.Duration;


@RestController
@RequestMapping("/auth")
public class AuthController {

@Autowired
private JwtDecoder jwtDecoder;
@Autowired
private KeycloakService keycloakService;
@Autowired
private UserService userService;
@PostMapping("/login")
public ResponseEntity<Void> login(@RequestBody LoginDTO cred) {
	AccessTokenResponse tok = keycloakService.login(cred.getUsername(), cred.getPassword());
	jwtDecoder.decode(tok.getToken());
	ResponseCookie cookie = ResponseCookie.from("KEYCLOAK_TOKEN" , tok.getToken())
	        .domain(".localhost")
			.httpOnly(true)
			.sameSite("None")
			.secure(true)
			.path("/")
			.maxAge(Duration.ofSeconds(tok.getExpiresIn())).build();
	return ResponseEntity.noContent()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.build();
}
@PostMapping("/logout")
public ResponseEntity<Void> logout() {
	ResponseCookie gone = ResponseCookie
			.from("KEYCLOAK_TOKEN", "").maxAge(0).path("/").build();
	return ResponseEntity.noContent()
			.header(HttpHeaders.SET_COOKIE, gone.toString())
			.build();
}
@PostMapping("/register")
public ResponseEntity<Void> register(@RequestBody RegisterDTO dto) {
 String kcId = keycloakService.createUser(dto);
	userService.createIfAbsent(kcId, dto.getUsername());

	return ResponseEntity.status(HttpStatus.CREATED).build();
}

}
