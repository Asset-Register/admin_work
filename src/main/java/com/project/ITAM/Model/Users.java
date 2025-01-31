package com.project.ITAM.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

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

    @ManyToOne
    @JoinColumn(name = "groupId") // Foreign key in the users table
    private Groups group;

    @Column(name="email")
    @Email(message = "Please provide a valid email address")
    private String email;

    @ManyToOne
    @JoinColumn(name = "roleId") // Foreign key in the users table
    private Role role;

    @Column(name="objects")
    private String objects;

    @Column(name="authentication")
    private String authentication;

    @Column(name="disabled")
    private String disabled;

    @ManyToMany(mappedBy = "allowedUsers")
    private Set<Folder> accessibleFolders = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Groups> groups = new HashSet<>();

    public Users() {
    }

    public Users(Long userId, String firstName, String lastName, String middleName, Groups group, String email, Role role, String objects, String authentication, String disabled, Set<Folder> accessibleFolders, Set<Groups> groups) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.group = group;
        this.email = email;
        this.role = role;
        this.objects = objects;
        this.authentication = authentication;
        this.disabled = disabled;
        this.accessibleFolders = accessibleFolders;
        this.groups = groups;
    }
}
