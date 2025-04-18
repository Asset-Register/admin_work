package com.project.ITAM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
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

    private Set<String> groupName;

    private Set<Role> roles = new HashSet<>();

    private Set<ObjectEntity> objects = new HashSet<>();


}
