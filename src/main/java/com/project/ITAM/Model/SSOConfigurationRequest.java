package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class SSOConfigurationRequest {
    private String ssoConfigureName;

    private String clientId;

    private String clientSecret;

    private String providerName;

    private String redirectUri;

    private String authorizationUri;

    private String tokenUri;

    private String scope;

    private boolean isEnable;

    private String userInfoUri;

}

