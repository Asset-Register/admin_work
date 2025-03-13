package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SmtpConfig")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmtpConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="host")
    private String host;
    @Column(name="port")
    private int port;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="encryptionType")
    private String encryptionType; // SSL, TLS, NONE
    @Column(name="fromEmail")
    private String fromEmail;
    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

    @Column(name="provider")
    private String provider;

}
