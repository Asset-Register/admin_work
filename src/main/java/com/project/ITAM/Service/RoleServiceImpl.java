package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.PermissionRepo;
import com.project.ITAM.Repository.RolesRepo;
import com.project.ITAM.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    LocalDateTime updateddate = LocalDateTime.now(ZoneId.systemDefault());
    String formattedDate = updateddate.format(DateTimeFormatter. ofPattern("yyyy-MM-dd HH:mm:ss"));


    @Override
    public Role createRole(RoleRequest roleRequest) {
        Set<Permission> permission = new HashSet<>();
        if(!CollectionUtils.isEmpty(roleRequest.getPermissionId())) {
            permission = new HashSet<>(permissionRepo.findAllById(roleRequest.getPermissionId()));
            if (permission.isEmpty()) {
                throw new NotFoundException("permission Id not found");
            }
        }
        return rolesRepo.save(Role.builder().createdBy("default").createdTime(formattedDate)
                .permissions(permission).roleName(roleRequest.getRoleName()).disabled(roleRequest.getDisabled()).build());
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
        if(!CollectionUtils.isEmpty(roleRequest.getPermissionId())) {
          Set<Permission>  permission = new HashSet<>(permissionRepo.findAllById(roleRequest.getPermissionId()));
            if (permission.isEmpty()) {
                throw new NotFoundException("permission Id not found");
            }else{
                role.setPermissions(permission);
            }
        }
        if(!StringUtils.isEmpty(roleRequest.getRoleName())) {
            role.setRoleName(roleRequest.getRoleName());
        }
        if(!StringUtils.isEmpty(roleRequest.getDisabled())) {
            role.setDisabled(roleRequest.getDisabled());
        }
role.setUpdatedBy("default");
        role.setUpdatedTime(formattedDate);
        return rolesRepo.save(role);
    }

    @Override
    public void deleteRoleById(Long roleId) {
        if (!rolesRepo.existsById(roleId)) {
            throw new NotFoundException("role with ID " + roleId + " not found");
        }
        Role role = rolesRepo.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        // Remove the role from all users before deleting
        for (Users user : userRepo.findAll()) {
            user.getRoles().remove(role);
        }
        userRepo.saveAll(userRepo.findAll());
        rolesRepo.deleteById(roleId);
    }
}
