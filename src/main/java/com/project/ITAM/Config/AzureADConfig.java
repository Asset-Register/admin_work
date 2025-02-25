/*

package com.project.ITAM.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

@Component
public class AzureADConfig {

    @Value("${security.mode}")
    private String clientId;

    @Value("${security.mode}")
    private String clientSecret;

    @Value("${security.mode}")
    private String authority;


    private ClientRegistration azureAdClientRegistration() {
        return ClientRegistration.withRegistrationId("azure")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("https://localhost:8082/login/oauth2/code/f1db1ed5-5658-49cf-956b-adcaa3b0f7e4")
                .scope("openid", "profile", "email")
                .authorizationUri(authority + "/oauth2/v2.0/authorize")
                .tokenUri(authority + "/oauth2/v2.0/token")
                .userInfoUri(authority + "/oauth2/v2.0/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .clientName("Azure AD")
                .build();
    }
}

*/
