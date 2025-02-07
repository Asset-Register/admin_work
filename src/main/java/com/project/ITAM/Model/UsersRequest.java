package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UsersRequest {

    private String firstName;

    private String lastName;

    private String middleName;

    private List<Long> groupId;

    private String email;

    private List<Long> roleId;

    private List<Long> objectId;

    private String authentication;

    private String disabled;

    private String password;

    public UsersRequest() {
    }

    public UsersRequest(String firstName, String lastName, String middleName, List<Long> groupId, String email, List<Long> roleId, List<Long> objects, String authentication, String disabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.groupId = groupId;
        this.email = email;
        this.roleId = roleId;
        this.objectId = objects;
        this.authentication = authentication;
        this.disabled = disabled;
    }
}
