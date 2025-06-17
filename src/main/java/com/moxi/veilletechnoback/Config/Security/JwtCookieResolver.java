package com.moxi.veilletechnoback.Config.Security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import jakarta.servlet.http.Cookie;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

import java.util.Arrays;

public class JwtCookieResolver implements BearerTokenResolver {
private final DefaultBearerTokenResolver delegate = new DefaultBearerTokenResolver();
@Override
public String resolve(HttpServletRequest request) {
	Cookie[] cookies = request.getCookies();
	if (cookies == null) return null;
	return Arrays.stream(cookies)
			.filter(c -> "KEYCLOAK_TOKEN".equals(c.getName()))
			.map(Cookie::getValue)
			.findFirst()
			.orElseGet(() -> delegate.resolve(request));
}
}
