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

}
