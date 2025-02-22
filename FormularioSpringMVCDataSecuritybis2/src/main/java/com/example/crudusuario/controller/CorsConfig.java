package com.example.crudusuario.controller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5175")); // ✅ Allow frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // ✅ Allow all methods
        config.setAllowedHeaders(List.of("Content-Type", "Authorization")); // ✅ Allow necessary headers
        config.setAllowCredentials(true); // ✅ Allow credentials (cookies, authentication)
        config.setMaxAge(3600L); // ✅ Cache preflight for 1 hour

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
