package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RoleRequest {

    private List<Long> permissionId;

    private String roleName;

    private String disabled;


}
