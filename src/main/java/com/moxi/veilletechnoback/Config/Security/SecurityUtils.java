package com.moxi.veilletechnoback.Config.Security;

import com.moxi.veilletechnoback.User.User;
import com.moxi.veilletechnoback.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtils {
@Autowired
private UserService userService;

public User getCurrentUser() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
		Jwt jwt = (Jwt) authentication.getPrincipal();
		return userService.findById(jwt.getSubject()).orElse(null);

	}
	return null;
}

}