/*
package com.project.ITAM.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class SecureUtil {

    public String getLoginUser(UserDetails userDetails1) {
        if(userDetails1!=null) {
            String userId1 = userDetails1.getUsername();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        String userId;
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            userId = userDetails.getUsername();
        } else if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            userId = oidcUser.getEmail(); // or getSubject() based on your setup
        } else {
            userId = authentication.getName(); // Default fallback
        }
        return  userId;
    }
}
*/
