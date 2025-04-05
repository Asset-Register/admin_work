package com.project.ITAM.Controller;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.Permission;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Model.RoleRequest;
import com.project.ITAM.Repository.PermissionRepo;
import com.project.ITAM.Repository.RolesRepo;
import com.project.ITAM.Service.RoleService;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/data/roles")
@CrossOrigin
@Validated
public class RoleController {

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
            private RoleService roleService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public ResponseEntity<Role> addRole(@Valid @RequestBody RoleRequest role) {
        logger.info("Create Role with permissionId:" + role.getRoleName());
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @PatchMapping("/{roleId}/update")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody RoleRequest role, @PathVariable Long roleId){
        logger.info("update Role :" + role.getRoleName());
        return ResponseEntity.ok(roleService.updateRoleById(role,roleId));
    }

    @DeleteMapping("/{roleId}/delete")
    public String deleteRole(@PathVariable Long roleId){
       roleService.deleteRoleById(roleId);
       if(!rolesRepo.existsById(roleId)){
           return "role deleted";
       }else{
           return "role not deleted";
       }
    }

    @GetMapping("/{role_id}/read")
    public ResponseEntity<Role> getRole(@PathVariable Long id){
       return  ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<Role>> getRole(){
        return  ResponseEntity.ok(roleService.getAllRoles());
    }

}
