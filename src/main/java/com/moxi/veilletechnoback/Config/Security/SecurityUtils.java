package com.moxi.veilletechnoback.Config.Security;

import com.moxi.veilletechnoback.User.CustomUserDetails.CustomUserDetails;
import com.moxi.veilletechnoback.User.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
public static User getCurrentUser() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails instanceof CustomUserDetails) {
			CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
			return customUserDetails.getUser();
		}
	}
	return null;
}

}