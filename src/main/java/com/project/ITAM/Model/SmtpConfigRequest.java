package com.project.ITAM.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmtpConfigRequest {
    private String host;
    private String provider;
    private int port;
    private String username;
    private String password;
    private String encryptionType; // SSL, TLS, NONE
    private String fromEmail;
    private String whiteListedDomain;
}
