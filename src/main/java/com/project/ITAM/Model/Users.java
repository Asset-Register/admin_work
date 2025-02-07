package com.project.ITAM.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@Table(name= "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="middleName")
    private String middleName;

    @Column(name="email",unique = true, nullable = false)
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(name="authentication")
    private String authentication;

    @Column(name="disabled")
    private String disabled;

    @ManyToMany(mappedBy = "allowedUsers")
    private Set<Folder> accessibleFolders = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Groups> groups = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_objects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "object_id")
    )
    private Set<ObjectEntity> objects = new HashSet<>();

    @Lob
    @Column(name="password")
    private String password;

    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

    @Column(name="lastLoginTime")
    private String lastLoginTime;

    public Users() {
    }

    public Users(Long userId, String firstName, String lastName, String middleName, String email, String authentication, String disabled, Set<Folder> accessibleFolders, Set<Groups> groups, Set<Role> roles, Set<ObjectEntity> objects, String password, String createdBy, String updatedBy, String createdTime, String updatedTime, String lastLoginTime) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.authentication = authentication;
        this.disabled = disabled;
        this.accessibleFolders = accessibleFolders;
        this.groups = groups;
        this.roles = roles;
        this.objects = objects;
        this.password = password;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.lastLoginTime = lastLoginTime;
    }
}
