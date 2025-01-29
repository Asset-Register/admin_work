package com.project.ITAM.Controller;

import com.project.ITAM.Model.Permission;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Repository.PermissionRepo;
import com.project.ITAM.Repository.RolesRepo;
import jakarta.validation.Valid;
import jakarta.ws.rs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@Validated
public class RoleController {

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public String addRole(@Valid @RequestBody Role role) {
        Permission groups = permissionRepo.save(role.getPermission());
        Role role1 = rolesRepo.save(role);
        if (role1.getRoleId() != null) {
            return "role Added";
        } else {
            return "role not Added";
        }
    }

    @PatchMapping("/update")
    public String updateRole(@Valid @RequestBody Role role){
        boolean id = rolesRepo.existsById(role.getRoleId());
        String result = null;
        if(id){
            Role role1 = rolesRepo.save(role);
        }else{
            result = "Id does not exist";
        }
        return result ;
    }

    @DeleteMapping("/delete")
    public String deleteRole(@RequestParam Long id){
       rolesRepo.deleteById(id);
       if(!rolesRepo.existsById(id)){
           return "role deleted";
       }else{
           return "role not deleted";
       }
    }

    @GetMapping("/read")
    public ResponseEntity<Object> getUser(@QueryParam("id") Long id){
        Optional<Role> role= rolesRepo.findById(id);
        if(role.isPresent()) {
            return ResponseEntity.ok(role.get());
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SQL execution failed");
        }
    }

}
