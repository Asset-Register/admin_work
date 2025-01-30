package com.project.ITAM.Controller;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.Groups;
import com.project.ITAM.Model.Users;
import com.project.ITAM.Model.UsersRequest;
import com.project.ITAM.Repository.GroupRepo;
import com.project.ITAM.Repository.UserRepo;
import com.project.ITAM.Service.UsersService;
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
@RequestMapping("/api/user")
@Validated
public class userController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
            private UsersService usersService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public ResponseEntity<Users> addUser(@Valid @RequestBody UsersRequest usersRequest){

        return ResponseEntity.ok(usersService.createUser(usersRequest));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Users> updateUser(UsersRequest usersRequest,@PathVariable Long id){

        return ResponseEntity.ok(usersService.updateUsersById(usersRequest,id));
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@RequestParam Long id){
        usersService.deleteUsersById(id);
        if(!userRepo.existsById(id)){
            return "User deleted";
        }else{
            return "user not deleted";
        }
    }

    @GetMapping("/{id}/read")
    public ResponseEntity<Users> getUser(@PathVariable Long id){

        return ResponseEntity.ok(usersService.getUsersById(id));
    }

}
