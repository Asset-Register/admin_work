package com.project.ITAM.Controller;

import com.project.ITAM.Model.Groups;
import com.project.ITAM.Model.Users;
import com.project.ITAM.Repository.GroupRepo;
import com.project.ITAM.Repository.UserRepo;
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
    private GroupRepo groupRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public String addUser(@Valid @RequestBody Users users){
        Groups groups = groupRepo.save(users.getGroup());
       Users result = userRepo.save(users);
        logger.info("user Saved:" + result.getUserId());
        if(result.getUserId()!=null) {
            return "User Added";
        }else{
            return "error while add the User";
        }
    }

    @PatchMapping("/update")
    public String updateUser(Users users){
        String result = null;
        boolean id = userRepo.existsById(users.getUserId());
        logger.info("User info updated:" + users.getUserId());
  if(id){
     Users users1= userRepo.save(users);
  }else{
      result = "id does not exist";
  }
        return result;
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam Long id){
       userRepo.deleteById(id);
        logger.info("deleted userId:" + id);
        if(!userRepo.existsById(id)){
            return "user deleted";
        }else {
            return "user not deleted";
        }
    }

    @GetMapping("/read")
    public ResponseEntity<Object> getUser(@QueryParam("id") Long id){
        Optional<Users> users= userRepo.findById(id);
if(users.isPresent()) {
    return ResponseEntity.ok(users.get());
}else{
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SQL execution failed");
}
    }

}
