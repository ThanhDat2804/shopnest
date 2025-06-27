package com.mall.shopnest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {
    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter() {
        // Create a new CORS configuration
        CorsConfiguration config = new CorsConfiguration();
        // Allow requests from all domains/origins
        config.addAllowedOriginPattern("*");
        // Allow sending cookies across origins
        config.setAllowCredentials(true);
        // Allow all original request headers
        config.addAllowedHeader("*");
        // Allow all HTTP request methods (e.g., GET, POST, PUT, etc.)
        config.addAllowedMethod("*");
        // Create a source for URL-based CORS configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply the CORS configuration to all paths (/**)
        source.registerCorsConfiguration("/**", config);
        // Return a new CORS filter with the configured source
        return new CorsFilter(source);
    }
}
