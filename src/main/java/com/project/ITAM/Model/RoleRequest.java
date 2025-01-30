package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoleRequest {

    private Long permissionId;

    private String roleName;

    private String disabled;


}
