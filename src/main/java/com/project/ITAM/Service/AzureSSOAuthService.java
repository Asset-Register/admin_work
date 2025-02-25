/*
package com.project.ITAM.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AzureSSOAuthService {

    private final RestTemplate restTemplate;
    private final String clientId = "your-client-id";
    private final String clientSecret = "your-client-secret";
    private final String tenantId = "your-tenant-id";
    private final String tokenUrl = "https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/token";

    public AzureSSOAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String authenticate(String username, String password) {
        // Prepare request parameters
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "password");
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("scope", "openid profile email offline_access");
        requestBody.put("username", username);
        requestBody.put("password", password);

        // Send request to Azure AD
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return (String) response.getBody().get("access_token"); // Return the access token
        } else {
            throw new RuntimeException("Azure authentication failed");
        }
    }
}
*/
