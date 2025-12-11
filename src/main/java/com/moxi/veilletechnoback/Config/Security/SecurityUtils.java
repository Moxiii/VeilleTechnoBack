package com.moxi.veilletechnoback.Config.Security;

import com.moxi.veilletechnoback.User.User;
import com.moxi.veilletechnoback.User.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SecurityUtils {

private final UserService userService;

public User getCurrentUser() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
		Jwt jwt = (Jwt) authentication.getPrincipal();
		return userService.findById(jwt.getSubject()).orElseThrow(()->new RuntimeException("User not found"));

	}
	return null;
}

}