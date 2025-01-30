package com.project.ITAM.Service;

import com.project.ITAM.Model.Permission;
import com.project.ITAM.Model.PermissionRequest;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Model.RoleRequest;

public interface PermissionService {
    public Permission createPermission(PermissionRequest permissionRequest);

    public Permission getPermissionById(Long roleId);

    public Permission updatePermissionById(PermissionRequest permissionRequest,Long roleId);

    public void deletePermissionById(Long roleId);

}
