package com.project.ITAM.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.mode}")
    private String securityMode;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        switch (securityMode.toLowerCase()) {
            case "sso":
                configureSSOSecurity(http);
                break;
            case "basic":
                configureBasicAuthSecurity(http);
                break;
            default:
                configureNoSecurity(http);
                break;
        }
        return http.build();
    }

    private void configureSSOSecurity(HttpSecurity http) throws Exception {
       /* http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Enable JWT authentication*/
    }

    private void configureBasicAuthSecurity(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()  // Public endpoints
                        .anyRequest().authenticated()  // Secure all other endpoints
                )
                .httpBasic(httpBasic -> {});
    }

    private void configureNoSecurity(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
