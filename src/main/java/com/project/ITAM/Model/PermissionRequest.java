package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PermissionRequest {

    private String permissionName;

    private Long roleId;

    private String type;


}
