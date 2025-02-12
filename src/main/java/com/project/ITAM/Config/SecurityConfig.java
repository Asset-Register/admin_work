package com.project.ITAM.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").authenticated()
                        //      .requestMatchers("/api/**").permitAll()  // Public endpoints
                        .anyRequest().permitAll()  // Secure all other endpoints
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(authenticationSuccessHandler()) // Custom handler
                );
    }

    private void configureBasicAuthSecurity(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()  // Public endpoints
                        .anyRequest().authenticated()  // Secure all other endpoints
                )
                .httpBasic(httpBasic -> {});
    }

    private void configureNoSecurity(HttpSecurity http) throws Exception {
        http
                //  .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers("/api/auth/**").authenticated()  // Public endpoints
                        .anyRequest().permitAll()
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  /*  public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }*/

    /*@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }*/

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            System.out.println("User authenticated: " + user.getAttributes());

            // Redirect to Swagger UI after successful authentication
            response.sendRedirect("/swagger-ui/index.html");
        };
    }
}
