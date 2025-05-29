package com.moxi.veilletechnoback.Config.JWT;

import com.moxi.veilletechnoback.User.CustomUserDetails.CustomUserDetailsService;
import com.moxi.veilletechnoback.User.User;
import com.moxi.veilletechnoback.User.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

private JwtUtils jwtUtil;

private CustomUserDetailsService customUserDetailsService;

private UserService userService;

public JwtAuthenticationFilter(JwtUtils jwtUtil, CustomUserDetailsService customUserDetailsService, UserService userService) {
	this.jwtUtil = jwtUtil;
	this.customUserDetailsService = customUserDetailsService;
	this.userService = userService;
}


/**
 * Token Filter
 * Le Filter fait en sorte de recuperer le token dans la requete puis il extrait le nom d'utilisateur >
 * si le token est bon, on met le security context et ont stock ce token jusqu'à expiration (1h)
 * @Filter
 * @Token
 */

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	String token = jwtUtil.extractTokenFromCookie(request);

	if (token != null && jwtUtil.validateToken(token)) {
		User currentUser = userService.findById(jwtUtil.extractUserId(token));
		if (currentUser != null) {
			String username = currentUser.getUsername();
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			System.out.println("Authentication set for user: " + username);
		} else {
			System.out.println("User not found for token");
		}
	} else {
		System.out.println("Token invalid or null");
	}

	filterChain.doFilter(request, response);
}

@Override
protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	String path = request.getRequestURI();
	return path.startsWith("/ws") || path.startsWith("/socket.io/");
}

private void updateSecurityContext(HttpServletRequest request, String username) {
	UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(authentication);
}}