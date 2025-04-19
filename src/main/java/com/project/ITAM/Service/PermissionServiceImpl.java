package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.Permission;
import com.project.ITAM.Model.PermissionRequest;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Repository.PermissionRepo;
import com.project.ITAM.Repository.RolesRepo;
import com.project.ITAM.helper.DateTimeUtil;
import com.project.ITAM.helper.ExtractJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Permission createPermission(PermissionRequest permissionRequest) {
        return permissionRepo.save(Permission.builder().createdBy(ExtractJsonUtil.getUserdetails())
                .createdTime(DateTimeUtil.currentDateTime())
                .permissionName(permissionRequest.getPermissionName()).type(permissionRequest.getType()).build());
    }

    @Override
    public Permission getPermissionById(Long permissionId) {
        Permission permission = permissionRepo.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        return permission;
    }

    @Override
    public List<Permission> getAllPermission() {
        return permissionRepo.findAll();
    }

    @Override
    public Permission updatePermissionById(PermissionRequest permissionRequest, Long permissionId) {
        Permission permission = permissionRepo.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        if(!StringUtils.isEmpty(permissionRequest.getPermissionName())) {
            permission.setPermissionName(permissionRequest.getPermissionName());
        }
        if(!StringUtils.isEmpty(permissionRequest.getType())) {
            permission.setType(permissionRequest.getType());
        }
        permission.setUpdatedBy(ExtractJsonUtil.getUserdetails());
        permission.setUpdatedTime(DateTimeUtil.currentDateTime());
        return permissionRepo.save(permission);
    }

    @Override
    public void deletePermissionById(Long permissionId) {
        if (!permissionRepo.existsById(permissionId)) {
            throw new NotFoundException("permission with ID " + permissionId + " not found");
        }
        Permission permission = permissionRepo.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        for(Role roles:rolesRepo.findAll()){
            roles.getPermissions().remove(permission);
        }
        rolesRepo.saveAll(rolesRepo.findAll());
           permissionRepo.deleteById(permissionId);
    }
}
