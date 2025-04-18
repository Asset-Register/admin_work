package com.project.ITAM.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String authentication;

    private String disabled;

    private List<String> groupName;
    private List<String> rolesName;
    private List<String> objectName;
   // private Set<Role> roles = new HashSet<>();

   // private Set<ObjectEntity> objects = new HashSet<>();


}
