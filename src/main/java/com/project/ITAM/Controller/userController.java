package com.project.ITAM.Controller;

import com.project.ITAM.Model.*;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin
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
    public ResponseEntity<Users> updateUser(@RequestBody UsersRequest usersRequest,@PathVariable Long id){

        return ResponseEntity.ok(usersService.updateUsersById(usersRequest,id));
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id){
        usersService.deleteUsersById(id);
        return "user deleted";
    }

    @GetMapping("/{id}/read")
    public ResponseEntity<Users> getUser(@PathVariable Long id){

        return ResponseEntity.ok(usersService.getUsersById(id));
    }

    @PostMapping("/adduser/{user_id}/group/{group_id}")
    public ResponseEntity<Users> addUsertoGroup(@PathVariable Long userId,@PathVariable Long groupId){
        return ResponseEntity.ok(usersService.addUserToGroup(userId,groupId));
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<Users>> getAllUsers(){
        return  ResponseEntity.ok(usersService.getAllUsers());
    }

}
