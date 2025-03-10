package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "sso_config")
@Builder
@Data
public class SsoConfig {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ssoConfigurationName")
    private String ssoConfigurationName;
    @Column(name="providerName")
    private String providerName;
    @Column(name="clientId")
    private String clientId;
    @Column(name="clientSecret")
    private String clientSecret;
    @Column(name="redirectUri")
    private String redirectUri;
    @Column(name="authorizationUri")
    private String authorizationUri;
    @Column(name="tokenUri")
    private String tokenUri;
    @Column(name="userInfoUri")
    private String userInfoUri;
    @Column(name="scope")
    private String scope;
    @Column(name="enabled")
    private boolean enabled;
    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

    public SsoConfig() {
    }

    public SsoConfig(Long id, String ssoConfigurationName, String providerName, String clientId, String clientSecret, String redirectUri, String authorizationUri, String tokenUri, String userInfoUri, String scope, boolean enabled, String createdBy, String updatedBy, String createdTime, String updatedTime) {
        this.id = id;
        this.ssoConfigurationName = ssoConfigurationName;
        this.providerName = providerName;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.authorizationUri = authorizationUri;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
        this.scope = scope;
        this.enabled = enabled;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
