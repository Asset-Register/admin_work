package com.project.ITAM.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class JwtTokenProvider {

    private final String JWT_SECRET = "lQUT9DAWSf+uR3MPcKBZMNiI9MmxT0O9Arm3b9k3cN4CHPFHKcnD7FHvz8nk7HFHOMmfR/u1V4J68IOPWAWudg=="; // Make sure to keep this secure

    public boolean validateToken(String token) {
        byte[] decodedKey = Base64.getDecoder().decode(JWT_SECRET);
        // Create the signing key
        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
        try {
            Jwts.parser().setSigningKey(keySpec).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

  /*  public String generateKey(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[64]; // 512 bits = 64 bytes
        secureRandom.nextBytes(key);

        // Optionally encode to Base64 for easier use
        return Base64.getEncoder().encodeToString(key);
    }*/

    public Authentication getAuthentication(String token) {
        byte[] decodedKey = Base64.getDecoder().decode(JWT_SECRET);
        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());

        Claims claims = Jwts.parser()
                .setSigningKey(keySpec)
                .parseClaimsJws(token)
                .getBody();

        User principal = new User(claims.getSubject(), "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }
}
