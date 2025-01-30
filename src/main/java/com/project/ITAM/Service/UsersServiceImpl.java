package com.project.ITAM.Service;

import com.project.ITAM.Model.Groups;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Model.Users;
import com.project.ITAM.Model.UsersRequest;
import com.project.ITAM.Repository.GroupRepo;
import com.project.ITAM.Repository.RolesRepo;
import com.project.ITAM.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Override
    public Users createUser(UsersRequest usersRequest) {
        Groups groups= new Groups();
        Role role = new Role();

        if(usersRequest.getGroupId()!=null){
            groups = groupRepo.findById(usersRequest.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));
        }
        if(usersRequest.getRoleId()!=null){
            role = rolesRepo.findById(usersRequest.getRoleId())
                    .orElseThrow(() -> new RuntimeException("role not found"));
        }
        return userRepo.save(Users.builder().group(groups).role(role).email(usersRequest.getEmail())
                .disabled(usersRequest.getDisabled()).authentication(usersRequest.getAuthentication()).objects(usersRequest.getObjects())
                .firstName(usersRequest.getFirstName()).objects(usersRequest.getObjects())
                .lastName(usersRequest.getLastName()).middleName(usersRequest.getMiddleName()).build());
    }

    @Override
    public Users getUsersById(Long userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return users;
    }

    @Override
    public Users updateUsersById(UsersRequest usersRequest, Long userId) {
        Users users = new Users();
        Role role = new Role();
        if(usersRequest.getRoleId()!=null) {
            role =rolesRepo.findById(usersRequest.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            users.setRole(role);
        }
        Groups groups= new Groups();
        if(usersRequest.getGroupId()!=null){
            groups = groupRepo.findById(usersRequest.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            users.setGroup(groups);
        }
        if(!StringUtils.isEmpty(usersRequest.getEmail())) {
            users.setEmail(usersRequest.getEmail());
        }
        if(!StringUtils.isEmpty(usersRequest.getAuthentication())) {
            users.setAuthentication(usersRequest.getAuthentication());
        }
        if(!StringUtils.isEmpty(usersRequest.getObjects())) {
            users.setObjects(usersRequest.getObjects());
        }
        if(!StringUtils.isEmpty(usersRequest.getLastName())) {
            users.setLastName(usersRequest.getLastName());
        }
        if(!StringUtils.isEmpty(usersRequest.getFirstName())) {
            users.setFirstName(usersRequest.getFirstName());
        }
        if(!StringUtils.isEmpty(usersRequest.getMiddleName())) {
            users.setMiddleName(usersRequest.getMiddleName());
        }
        if(!StringUtils.isEmpty(usersRequest.getDisabled())) {
            users.setDisabled(usersRequest.getDisabled());
        }
        return userRepo.save(users);
    }

    @Override
    public void deleteUsersById(Long userId) {
         userRepo.deleteById(userId);
    }

    }
