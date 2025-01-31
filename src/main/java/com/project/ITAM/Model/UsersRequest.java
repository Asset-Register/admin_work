package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsersRequest {

    private String firstName;

    private String lastName;

    private String middleName;

    private Long groupId;

    private String email;

    private Long roleId;

    private Long objectId;

    private String authentication;

    private String disabled;

    public UsersRequest() {
    }

    public UsersRequest(String firstName, String lastName, String middleName, Long groupId, String email, Long roleId, Long objects, String authentication, String disabled) {
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
