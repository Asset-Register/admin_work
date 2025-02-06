package com.project.ITAM.Controller;

import com.project.ITAM.Model.Permission;
import com.project.ITAM.Model.PermissionRequest;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Model.RoleRequest;
import com.project.ITAM.Repository.PermissionRepo;
import com.project.ITAM.Repository.RolesRepo;
import com.project.ITAM.Service.PermissionService;
import com.project.ITAM.Service.RoleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@CrossOrigin
@Validated
public class PermissionController {

    @Autowired
    private  PermissionRepo permissionRepo;

    @Autowired
            private PermissionService permissionService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public ResponseEntity<Permission> addPermission(@Valid @RequestBody PermissionRequest permissionRequest) {
        logger.info("Create permission:" + permissionRequest.getPermissionName());
        return ResponseEntity.ok(permissionService.createPermission(permissionRequest));
    }

    @PatchMapping("/{permission_id}/update")
    public ResponseEntity<Permission> updatePermission(@Valid @RequestBody PermissionRequest permissionRequest, @PathVariable("permission_id") Long permissionId){
        logger.info("update permission :" + permissionRequest.getPermissionName());
        return ResponseEntity.ok(permissionService.updatePermissionById(permissionRequest,permissionId));
    }

    @DeleteMapping("/{permission_id}/delete")
    public String deletePermission(@PathVariable("permission_id") Long id){
        permissionService.deletePermissionById(id);
       if(!permissionRepo.existsById(id)){
           return "permission deleted";
       }else{
           return "permission not deleted";
       }
    }

    @GetMapping("/{permission_id}/read")
    public ResponseEntity<Permission> getPermission(@PathVariable("permission_id") Long id){
       return  ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<Permission>> getPermission(){
        return  ResponseEntity.ok(permissionService.getAllPermission());
    }

}
