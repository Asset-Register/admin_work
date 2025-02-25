/*
package com.project.ITAM.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.security.Key;
import java.util.Base64;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtUtil {
    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generates a secure key
    static String base64Key = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
    private static final String SECRET_KEY = base64Key ; // Store securely

    public static Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        return claims.get("userId", String.class);
    }
}
*/
