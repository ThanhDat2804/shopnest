package com.mall.shopnest.security.config;

import com.mall.shopnest.security.component.DynamicAuthorizationManager;
import com.mall.shopnest.security.component.JwtAuthenticationTokenFilter;
import com.mall.shopnest.security.component.RestAuthenticationEntryPoint;
import com.mall.shopnest.security.component.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired(required = false)
    private DynamicAuthorizationManager dynamicAuthorizationManager;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(registry -> {
                    // Allow access to resources that don't require protection
                    for (String url : ignoreUrlsConfig.getUrls()) {
                        registry.requestMatchers(url).permitAll();
                    }
                    // Allow OPTIONS requests for cross-origin requests
                    registry.requestMatchers(HttpMethod.OPTIONS).permitAll();
                    // All other requests require authentication
                })
                // All other requests require authentication
                .authorizeHttpRequests(registry -> registry.anyRequest()
                        // If dynamic permission configuration is present, use the dynamic permission manager
                        .access(dynamicAuthorizationManager == null ? AuthenticatedAuthorizationManager.authenticated() : dynamicAuthorizationManager)
                )
                // Disable CSRF protection
                .csrf(AbstractHttpConfigurer::disable)
                // Set session creation policy to stateless (no sessions will be created)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Custom handler for access denied responses
                .exceptionHandling(configurer -> configurer.accessDeniedHandler(restfulAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint))
                // Add custom JWT authentication filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
