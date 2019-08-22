package com.zl.way;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zl.way.aspect.WayTokenValidationInterceptor;

/**
 * @author xuzhongliang
 */
@Configuration
public class WayTokenValidationWebConfig implements WebMvcConfigurer {

    @Bean
    public WayTokenValidationInterceptor wayTokenValidationInterceptor() {
        return new WayTokenValidationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(wayTokenValidationInterceptor()).addPathPatterns("/mp/**");
    }
}
