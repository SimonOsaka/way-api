package com.zl.way;

import com.zl.way.aspect.WebSecurityValidateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public WebSecurityValidateInterceptor webSecurityValidateInterceptor() {
		return new WebSecurityValidateInterceptor();
	}

	@Override

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(webSecurityValidateInterceptor());
	}
}
