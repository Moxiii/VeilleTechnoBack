package com.moxi.veilletechnoback.Config.JWT.Interceptor;

import com.moxi.veilletechnoback.Config.JWT.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.moxi.veilletechnoback.Config.JWT.Annotation.RequireAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
@Autowired
private JwtUtils jwtUtil;

@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	if(handler instanceof HandlerMethod) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		RequireAuthorization requireAuthorization = handlerMethod.getMethodAnnotation(RequireAuthorization.class);
		String uri = request.getRequestURI();
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}
		if (uri.startsWith("/api/auth")) {
			return true;
		}
		if(requireAuthorization != null) {
			String token = jwtUtil.extractTokenFromCookie(request);
			if(token != null || !jwtUtil.validateToken(token)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Unauthorized : Invalid or missing token");
				return false;
			}
		}
	}

	return true;
}

}
