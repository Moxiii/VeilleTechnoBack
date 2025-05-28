package com.moxi.veilletechnoback.User.CustomUserDetails;

import com.moxi.veilletechnoback.User.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
private final String username;
private final String password;

private final User user;
public CustomUserDetails(String username, String password , User user) {
	this.username = username;
	this.password = password;
	this.user = user;
}
public User getUser() {
	return user;
}
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	return authorities;
}

@Override
public String getPassword() {
	return password;
}

@Override
public String getUsername() {
	return username;
}

@Override
public boolean isAccountNonExpired() {
	return true;
}

@Override
public boolean isAccountNonLocked() {
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	return true;
}

@Override
public boolean isEnabled() {
	return true;
}
}
