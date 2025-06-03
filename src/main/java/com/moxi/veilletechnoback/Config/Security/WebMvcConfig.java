package com.moxi.veilletechnoback.Config.Security;

import com.moxi.veilletechnoback.Config.JWT.Interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
@Autowired
private AuthorizationInterceptor authorizationInterceptor;

@Override
public void addCorsMappings(CorsRegistry corsRegistry){
	corsRegistry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
			.allowCredentials(true)
			.allowedOrigins("http://localhost:5173" , "http://localhost:5174")
			.allowedHeaders("*");

}
@Override
public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(authorizationInterceptor);
}
}