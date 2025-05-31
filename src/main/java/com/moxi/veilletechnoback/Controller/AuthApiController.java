package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Config.JWT.JwtUtils;
import com.moxi.veilletechnoback.Config.JWT.TokenManager;
import com.moxi.veilletechnoback.DTO.AUTH.LOGIN.LoginReq;
import com.moxi.veilletechnoback.DTO.AUTH.LOGIN.LoginRes;
import com.moxi.veilletechnoback.User.User;
import com.moxi.veilletechnoback.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("api/auth")
public class AuthApiController {
@Autowired
private UserService userService;

@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private JwtUtils jwtUtil;
@Autowired
private TokenManager tokenManager;


@PostMapping("/register")
public ResponseEntity<String> createUser(@RequestBody User user) {
	try {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = dateFormat.format(new Date());
		user.setDateInscription(formattedDate);
		user.setPassword(encodedPassword);
		userService.saveUser(user);
		return new ResponseEntity<>("User creer avec sucess", HttpStatus.CREATED);
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>("Erreur dans la creation de l'user ", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@PostMapping(path = {"/login"})
@ResponseBody
public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	User existingUser = userService.findByUsername(loginReq.getUsername().toLowerCase());


	if (existingUser != null) {
		log.info("current user : {}", existingUser.getUsername());
		if (passwordEncoder.matches(loginReq.getPassword(), existingUser.getPassword())) {
			log.info("password matches");
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(existingUser.getUsername(), loginReq.getPassword());
				log.info("token : {}", token);
				try {
					Authentication authentication = authenticationManager.authenticate(token);
					if (authentication != null && authentication.isAuthenticated()) {
						SecurityContext sc = SecurityContextHolder.getContext();
						sc.setAuthentication(authentication);
						String accessToken = jwtUtil.createAccessToken(existingUser);
						String refreshToken = jwtUtil.createRefreshToken(existingUser);
						tokenManager.addToken(existingUser.getUsername() + "_refresh",refreshToken);
						tokenManager.addToken(existingUser.getUsername(), accessToken);
						ResponseCookie cookie = ResponseCookie.from("access_token" , accessToken)
								.maxAge(Duration.ofHours(1))
								.httpOnly(true)
								.secure(false)
								.sameSite("Lax")
								.path("/")
								.build();
						return ResponseEntity.ok()
								.header(HttpHeaders.SET_COOKIE , cookie.toString())
								.body(new LoginRes(existingUser.getUsername()));

					}
				} catch (AuthenticationException e) {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
		}
	} else {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
}

@DeleteMapping("/logout")
public ResponseEntity<String> logout(HttpServletRequest req) {
	String token = jwtUtil.extractTokenFromCookie(req);
	if (token != null) {
		User currentUser = userService.findById(jwtUtil.extractUserId(token));
		String username = userService.findById(currentUser.getId()).toString();
		if (username != null) {
			tokenManager.removeToken(username);
			SecurityContextHolder.getContext().setAuthentication(null);
			ResponseCookie cookie = ResponseCookie.from("access_token", "")
					.maxAge(0)
					.httpOnly(true)
					.secure(false)
					.sameSite("Lax")
					.path("/")
					.build();
			return ResponseEntity.status(HttpStatus.OK)
					.header(HttpHeaders.SET_COOKIE , cookie.toString())
					.body("User logged out successfully");
		}
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
}


@GetMapping("/status")
public ResponseEntity<?> getStatus(@CookieValue(name = "access_token" , required = false) String token) {
if (token != null && jwtUtil.validateToken(token)) {
	try{
	User currentUser = userService.findById(jwtUtil.extractUserId(token));
	return ResponseEntity.ok(Map.of("authenticated", true));
} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated", false));
	}
}
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated", false));
}
}

