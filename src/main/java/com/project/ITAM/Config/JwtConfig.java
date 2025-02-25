/*
package com.project.ITAM.Config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JwtConfig {

    private static final String SECRET_KEY = "r02VJs1fKpB8nT2dF7qP6zO7KLY1wZa3M3dD5L9eXH7U8oL1J5zQ"; // Secure 512-bit key

    @Bean
    public SecretKey jwtSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY); // Decode Base64 key
        return Keys.hmacShaKeyFor(keyBytes); // Ensure it is 512 bits (64 bytes)
    }
}
*/
