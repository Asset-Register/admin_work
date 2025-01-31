package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.Groups;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Model.Users;
import com.project.ITAM.Model.UsersRequest;
import com.project.ITAM.Repository.GroupRepo;
import com.project.ITAM.Repository.RolesRepo;
import com.project.ITAM.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

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
                    .orElse(null);
        }
        if(usersRequest.getRoleId()!=null){
            role = rolesRepo.findById(usersRequest.getRoleId())
                    .orElse(null);
        }
        return userRepo.save(Users.builder().group(groups).role(role).email(usersRequest.getEmail())
                .disabled(usersRequest.getDisabled()).authentication(usersRequest.getAuthentication()).objects(usersRequest.getObjects())
                .firstName(usersRequest.getFirstName()).objects(usersRequest.getObjects())
                .lastName(usersRequest.getLastName()).middleName(usersRequest.getMiddleName()).build());
    }

    @Override
    public Users getUsersById(Long userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return users;
    }

    @Override
    public Users updateUsersById(UsersRequest usersRequest, Long userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() ->  new NotFoundException( userId + " not found"));
        Optional<Role> role = Optional.of(new Role());
        if(usersRequest.getRoleId()!=null) {
            role =rolesRepo.findById(usersRequest.getRoleId());
            if(role.isPresent()) {
                users.setRole(role.get());
            }
        }
        Optional<Groups> groups= Optional.of(new Groups());
        if(usersRequest.getGroupId()!=null){
            groups = groupRepo.findById(usersRequest.getGroupId());
            if(groups.isPresent()) {
                users.setGroup(groups.get());
            }
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
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }
         userRepo.deleteById(userId);
    }

    @Override
    @Transactional
    public Users addUserToGroup(Long userId, Long groupId) {
        Optional<Users> userOpt = userRepo.findById(userId);
        Optional<Groups> groupOpt = groupRepo.findById(groupId);

        if (userOpt.isPresent() && groupOpt.isPresent()) {
            Users user = userOpt.get();
            Groups group = groupOpt.get();

            user.getGroups().add(group);
             return userRepo.save(user);
        } else {
            throw new NotFoundException("User or Group not found");
        }
    }

    }
