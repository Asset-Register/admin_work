package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.Permission;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Model.RoleRequest;
import com.project.ITAM.Repository.PermissionRepo;
import com.project.ITAM.Repository.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Override
    public Role createRole(RoleRequest roleRequest) {
        Permission permission = new Permission();
        if(roleRequest.getPermissionId()!=null){
            permission = permissionRepo.findById(roleRequest.getPermissionId())
                    .orElseThrow(() -> new NotFoundException("Parent folder not found"));
        }
        return rolesRepo.save(Role.builder().permission(permission).roleName(roleRequest.getRoleName()).disabled(roleRequest.getDisabled()).build());
    }

    @Override
    public Role getRoleById(Long roleId) {
        Role role = rolesRepo.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        return rolesRepo.findAll();
    }

    @Override
    public Role updateRoleById(RoleRequest roleRequest,Long roleId) {
         Role role = rolesRepo.findById(roleId)
                    .orElseThrow(() -> new NotFoundException("Role not found"));
        Permission permission = new Permission();
        if(roleRequest.getPermissionId()!=null){
            permission = permissionRepo.findById(roleRequest.getPermissionId())
                    .orElseThrow(() -> new NotFoundException("Permission not found"));
            role.setPermission(permission);
        }
        if(!StringUtils.isEmpty(roleRequest.getRoleName())) {
            role.setRoleName(roleRequest.getRoleName());
        }
        if(!StringUtils.isEmpty(roleRequest.getDisabled())) {
            role.setRoleName(roleRequest.getDisabled());
        }

        return rolesRepo.save(role);
    }

    @Override
    public void deleteRoleById(Long roleId) {
        if (!rolesRepo.existsById(roleId)) {
            throw new NotFoundException("role with ID " + roleId + " not found");
        }
       rolesRepo.deleteById(roleId);
    }
}
