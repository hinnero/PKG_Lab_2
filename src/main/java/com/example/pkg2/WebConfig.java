package com.example.pkg2;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/images/**")
                .allowedOrigins("http://localhost:63342")
                .allowedMethods("POST", "GET", "OPTIONS")
                .allowedHeaders("*");
    }
}