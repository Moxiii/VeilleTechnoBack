package com.moxi.veilletechnoback.User.CustomUserDetails;

import com.moxi.veilletechnoback.User.User;
import com.moxi.veilletechnoback.User.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
private final UserService userService;

public CustomUserDetailsService(UserService userService) {
	this.userService = userService;
}

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = userService.findByUsername(username);
	if (user == null) {
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

	return new CustomUserDetails(user.getUsername(), user.getPassword(), user);
}
}
