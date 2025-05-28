package com.moxi.veilletechnoback.Config.Security;

import com.moxi.veilletechnoback.Config.JWT.JwtAuthenticationFilter;
import com.moxi.veilletechnoback.Config.JWT.JwtUtils;
import com.moxi.veilletechnoback.User.CustomUserDetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


@Autowired
private CustomUserDetailsService customUserDetailsService;

@Autowired
private JwtUtils jwtUtil;

@Bean
public RestTemplate restTemplate() {
	return new RestTemplate();
}
@Bean
public AuthenticationManager authManager(HttpSecurity http) throws Exception {
	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	authenticationManagerBuilder.userDetailsService(customUserDetailsService);
	return authenticationManagerBuilder.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

@Bean
public StrictHttpFirewall httpFirewall() {
	StrictHttpFirewall firewall = new StrictHttpFirewall();
	firewall.setAllowSemicolon(true); // Allow semicolon in URLs
	return firewall;
}



@SuppressWarnings("deprecation")
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http
			.cors()
			.and()
			.csrf()
			.ignoringRequestMatchers("/api/**")
			.ignoringRequestMatchers("/chat/**")
			.ignoringRequestMatchers("/ws/**")
			.and()
			.authorizeRequests()
			.requestMatchers("/api/**").permitAll()
			.requestMatchers("/ws/**").permitAll()
			.requestMatchers("/admin/**").hasAnyRole("admin")
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			.and()
			.formLogin()
			.loginPage("/api/auth/login") // Specify the URL of your custom login page
			.loginProcessingUrl("/process-login")
			.defaultSuccessUrl("/index")
			.failureUrl("/custom-login?error=true")
			.permitAll()
			.successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
			.and()
			.logout()
			.logoutSuccessUrl("/api/auth/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.permitAll();

	return http.build();
}
}