package com.moxi.veilletechnoback.Config.JWT;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import jakarta.servlet.http.Cookie;

import java.util.Arrays;

public class JwtCookieResolver implements BearerTokenResolver {
@Override
public String resolve(HttpServletRequest request) {
	if (request.getCookies() == null) return null;
	return Arrays.stream(request.getCookies())
			.filter(c -> "KEYCLOAK_TOKEN".equals(c.getName()))
			.map(Cookie::getValue)
			.findFirst()
			.orElse(null);
}
}
