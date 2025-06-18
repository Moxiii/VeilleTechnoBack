package com.moxi.veilletechnoback.Config.Security;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


@Override
public void addCorsMappings(CorsRegistry corsRegistry){
	corsRegistry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
			.allowCredentials(true)
			.allowedOrigins(
					"http://localhost:5173",
					"http://localhost:4173",
					"http://veille.localhost"
			)
			.allowedHeaders("*");
}
}

