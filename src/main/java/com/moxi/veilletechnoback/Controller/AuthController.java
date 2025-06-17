package com.moxi.veilletechnoback.Controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthController {
private final JwtDecoder jwtDecoder;

public AuthController(JwtDecoder jwtDecoder) {
	this.jwtDecoder = jwtDecoder;
}
@PostMapping("/cookie")
public ResponseEntity<Void> storeToken(@RequestBody String token) {
	jwtDecoder.decode(token);
	ResponseCookie ck = ResponseCookie.from("KEYCLOAK_TOKEN", token)
			.httpOnly(true).secure(false)
			.sameSite("None").domain("localhost").path("/")
			.maxAge(Duration.ofHours(1))
			.build();
	return ResponseEntity.noContent()
			.header(HttpHeaders.SET_COOKIE, ck.toString())
			.build();

}
}
