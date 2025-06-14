package com.moxi.veilletechnoback.Config.Security;

import com.moxi.veilletechnoback.User.CustomUserDetails.CustomUserDetails;
import com.moxi.veilletechnoback.User.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {
@Autowired
private UserService userService;
public static User getCurrentUser() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
		Jwt jwt = (Jwt) authentication.getPrincipal();
		User user = userService.findById(jwt.getSubject();
		return user;
	}
	return null;
}

}