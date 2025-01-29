package com.project.ITAM.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name= "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false,name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="middleName")
    private String middleName;

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = false) // Foreign key in the users table
    private Groups group;

    @Column(name="email")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(name="roleId")
    private String roleId;

    @Column(name="objects")
    private String objects;

    @Column(name="authentication")
    private String authentication;

    @Column(name="disabled")
    private String disabled;

    public Users() {
    }

    public Users(Long userId, String firstName, String lastName, String middleName, Groups group, String email, String roleId, String objects, String authentication, String disabled) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.group = group;
        this.email = email;
        this.roleId = roleId;
        this.objects = objects;
        this.authentication = authentication;
        this.disabled = disabled;
    }
}
