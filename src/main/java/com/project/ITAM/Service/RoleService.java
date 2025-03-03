package com.project.ITAM.Service;

import com.project.ITAM.Model.Role;
import com.project.ITAM.Model.RoleRequest;

import java.util.List;

public interface RoleService {
    public Role createRole(RoleRequest role);

    public Role getRoleById(Long roleId);

    public Role updateRoleById(RoleRequest roleRequest,Long roleId);

    public void deleteRoleById(Long roleId);

    public List<Role> getAllRoles();

}
