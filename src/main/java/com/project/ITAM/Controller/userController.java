package com.project.ITAM.Controller;

import com.project.ITAM.Model.UserDTO;
import com.project.ITAM.Model.Users;
import com.project.ITAM.Model.UsersRequest;
import com.project.ITAM.Repository.UserRepo;
import com.project.ITAM.Service.UsersService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/user")
@Validated
@CrossOrigin
public class userController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
            private UsersService usersService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Add new user
     *
     * @param usersRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UsersRequest usersRequest) {
        try {
            String createdUser = usersService.createUser(usersRequest);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }

    /** update user details
     *
     * @param usersRequest
     * @param userId
     * @return
     */
    @PatchMapping("/{user_id}/update")
    public ResponseEntity<?> updateUser(@RequestBody UsersRequest usersRequest, @PathVariable("user_id") Long userId) {
        try {
            String updatedUser = usersService.updateUsersById(usersRequest, userId);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user: " + e.getMessage());
        }
    }

    /** update user with Roles
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @PatchMapping("/{userId}/Role/{roleIds}")
    public ResponseEntity<?> updateUserwithRoles(@PathVariable("userId") Long userId, @PathVariable("roleIds") String roleIds) {
        try {
            String updatedUser = usersService.updateUsersByRoleId(userId, roleIds);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user roles: " + e.getMessage());
        }
    }

    /** delete user
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            usersService.deleteUsersById(id);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }

    /** get User details based on Id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/read")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            UserDTO user = usersService.getUsersById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user: " + e.getMessage());
        }
    }

    /** Add user to Group
     *
     * @param userId
     * @param groupId
     * @return
     */
    @PostMapping("/adduser/{user_id}/group/{group_id}")
    public ResponseEntity<?> addUsertoGroup(@PathVariable("user_id") Long userId, @PathVariable("group_id") Long groupId) {
        try {
            String updatedUser = usersService.addUserToGroup(userId, groupId);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding user to group: " + e.getMessage());
        }
    }

    /** Read All user details
     *
     * @return
     */
    @GetMapping("/readAll")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> usersList = usersService.getAllUsers();
            return ResponseEntity.ok(usersList);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving users: " + e.getMessage());
        }
    }


}
