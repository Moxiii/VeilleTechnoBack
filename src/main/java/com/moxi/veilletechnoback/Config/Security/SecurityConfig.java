package com.moxi.veilletechnoback.Config.Security;

import com.moxi.veilletechnoback.Config.JWT.JwtCookieResolver;
import com.moxi.veilletechnoback.User.CustomUserDetails.CustomUserDetailsService;
import com.moxi.veilletechnoback.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


@Autowired
private CustomUserDetailsService customUserDetailsService;
@Autowired
private UserService userService;

@Bean
public RestTemplate restTemplate() {
	return new RestTemplate();
}
@Bean
public AuthenticationManager authManager(HttpSecurity http) throws Exception {
	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	return authenticationManagerBuilder.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

@Bean
public StrictHttpFirewall httpFirewall() {
	StrictHttpFirewall firewall = new StrictHttpFirewall();
	firewall.setAllowSemicolon(true);
	return firewall;
}


@SuppressWarnings("deprecation")
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http
			.cors()
			.and()
			.csrf()
			.disable()
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeRequests(auth -> auth
					.requestMatchers("/api/auth/**" , "/public/**").permitAll()
					.anyRequest().authenticated()
			)
			.oauth2ResourceServer(oauth -> oauth
					.bearerTokenResolver(new JwtCookieResolver())
					.jwt(Customizer.withDefaults())
			);

	return http.build();
}
}