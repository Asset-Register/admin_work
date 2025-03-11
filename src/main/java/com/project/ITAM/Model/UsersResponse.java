package com.project.ITAM.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String authentication;

    private String disabled;

    private Set<Folder> accessibleFolders = new HashSet<>();

    private Set<Groups> groups = new HashSet<>();

    private Set<Role> roles = new HashSet<>();

    private Set<ObjectEntity> objects = new HashSet<>();

    private String createdBy;

    private String updatedBy;

    private String createdTime;

    private String updatedTime;

}
