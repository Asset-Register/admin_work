package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sso_config")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name="jwkSetUri")
    private String jwkSetUri;

    @Column(name="issuerUri")
    private String issuerUri;

}
