package com.moxi.veilletechnoback.Config.JWT;

import com.moxi.veilletechnoback.User.CustomUserDetails.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Filter(name = "jwtAuthenticationFilter")
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
@Autowired
private JwtUtils jwtUtil;
@Autowired
private CustomUserDetailsService customUserDetailsService;



public JwtAuthenticationFilter(JwtUtils jwtUtil , CustomUserDetailsService customUserDetailsService) {
	this.jwtUtil = jwtUtil;
	this.customUserDetailsService = customUserDetailsService;

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
	String newToken = jwtUtil.checkToken(request);

	if (newToken != null) {
		response.setHeader("Authorization", "Bearer " + newToken);
		token = newToken;
	}

	if (token != null && jwtUtil.validateToken(token)) {
		String username = jwtUtil.extractUsername(token);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
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