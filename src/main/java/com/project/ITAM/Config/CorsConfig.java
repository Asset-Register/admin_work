package com.project.ITAM.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for all endpoints
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // Replace with the React app URL
                .allowedMethods("GET", "POST", "PATCH", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);  // If you're using cookies or authentication
    }
}
