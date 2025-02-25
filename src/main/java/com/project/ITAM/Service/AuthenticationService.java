/*
package com.project.ITAM.Service;

import com.project.ITAM.Model.AuthenticationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public void authenticate(AuthenticationType authType) {
        switch (authType) {
            case BASIC:
                configureSSOSecurity(username, password);
                break;
            case SSO:
                authenticateSSO(username);  // SSO Logic here (e.g., OAuth, OpenID, etc.)
                break;
            case NONE:
                SecurityContextHolder.clearContext(); // No authentication needed
                break;
            default:
                throw new IllegalArgumentException("Invalid Authentication Type");
        }
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
                        .requestMatchers("/authenticate/**")//.hasAnyRole("admin","user") // Public endpoints
                        .permitAll().anyRequest().authenticated()  // Secure all other endpoints
                )
                .httpBasic(httpBasic -> {});
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            System.out.println("User authenticated: " + user.getAttributes());

            // Redirect to Swagger UI after successful authentication
            response.sendRedirect("/swagger-ui/index.html");
        };
    }


    */
/*private void authenticateBasic(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void authenticateSSO(String username) {
        // Implement SSO authentication logic, e.g., using OAuth2 or OpenID Connect
        // Once authenticated, set the authentication into the SecurityContext

        // Sample SSO Authentication (add your own logic for SSO, such as OAuth2, JWT validation)
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }*//*

}
*/
