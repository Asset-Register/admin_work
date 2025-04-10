package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.*;
import com.project.ITAM.helper.DateTimeUtil;
import com.project.ITAM.helper.ExtractJsonUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private ObjectRepo objectRepo;

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
            private DashBoardRepo dashBoardRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Users createUser(UsersRequest usersRequest) {
        Set<Groups> groups= new HashSet<>();
        Set<Role> role = new HashSet<>();
        Set<ObjectEntity> objectEntity = new HashSet<>();

        if( !CollectionUtils.isEmpty(usersRequest.getGroupId())) {
            // Fetch users from the database
            groups = new HashSet<>(groupRepo.findAllById(usersRequest.getGroupId()));
        }
        if( !CollectionUtils.isEmpty(usersRequest.getRoleId())) {
            // Fetch groups from the database
            role = new HashSet<>(rolesRepo.findAllById(usersRequest.getRoleId()));
        }
        if( !CollectionUtils.isEmpty(usersRequest.getObjectId())) {
            // Fetch groups from the database
            objectEntity = new HashSet<>(objectRepo.findAllById(usersRequest.getObjectId()));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(usersRequest.getPassword()); // Encrypt password
        System.out.println("Hashed Password: " + hashedPassword);
      //  String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.save(Users.builder().email(usersRequest.getEmail())
                .disabled(usersRequest.getDisabled()).authentication(usersRequest.getAuthentication())
                .firstName(usersRequest.getFirstName()).groupMapped(groups).roles(role).objects(objectEntity)
                .lastName(usersRequest.getLastName()).middleName(usersRequest.getMiddleName()).password(hashedPassword)
                .createdBy(ExtractJsonUtil.getUserdetails()).createdTime(DateTimeUtil.currentDateTime()).build());
    }

    @Transactional
    @Override
    public Users getUsersById(Long userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Users users1 = userRepo.findByIdWithGroups(userId).orElseThrow(() -> new NotFoundException("User not found"));
        // Extract group names into a Set<String>
        Set<String> groupNames = users.getGroupMapped().stream()
                .map(Groups::getGroupName) // get the groupName from each Group
                .collect(Collectors.toSet()); // collect into a Set
        users.setGroupName(groupNames);
        return users;
    }

    @Override
    public Users updateUsersById(UsersRequest usersRequest, Long userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() ->  new NotFoundException( userId + " not found"));
        if(!CollectionUtils.isEmpty(usersRequest.getGroupId())) {
            // Fetch groups from the database
            Set<Groups> groups = new HashSet<>(groupRepo.findAllById(usersRequest.getGroupId()));
            // Update allowed groups
            users.setGroupMapped(groups);
        }
        if(!CollectionUtils.isEmpty(usersRequest.getRoleId())) {
            // Fetch roles from the database
            Set<Role> roles = new HashSet<>(rolesRepo.findAllById(usersRequest.getRoleId()));
            // Update allowed roles
            users.setRoles(roles);
        }
        if(!CollectionUtils.isEmpty(usersRequest.getObjectId())) {
            // Fetch groups from the database
            Set<ObjectEntity> objectEntities = new HashSet<>(objectRepo.findAllById(usersRequest.getObjectId()));
            // Update allowed groups
            users.setObjects(objectEntities);
        }
        if(!StringUtils.isEmpty(usersRequest.getEmail())) {
            users.setEmail(usersRequest.getEmail());
        }
        if(!StringUtils.isEmpty(usersRequest.getAuthentication())) {
            users.setAuthentication(usersRequest.getAuthentication());
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
        users.setUpdatedTime(DateTimeUtil.currentDateTime());
        users.setUpdatedBy(ExtractJsonUtil.getUserdetails());
        return userRepo.save(users);
    }

    @Override
    public Users updateUsersByRoleId(Long userId, String roleIds) {
        Set<Long> idSet = Arrays.stream(roleIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toSet());
        Users users = userRepo.findById(userId)
                .orElseThrow(() ->  new NotFoundException( userId + " not found"));
        if(!CollectionUtils.isEmpty(idSet)) {
            // Fetch roles from the database
            Set<Role> roles = new HashSet<>(rolesRepo.findAllById(idSet));
            if(!roles.isEmpty()) {
                // Update allowed roles
                users.setRoles(roles);
            }else{
              throw  new NotFoundException("enter valid role ids");
            }
        }else{
           throw  new NotFoundException("enter valid role id");
        }
        users.setUpdatedTime(DateTimeUtil.currentDateTime());
        users.setUpdatedBy(ExtractJsonUtil.getUserdetails());
        return users;
    }

    @Override
    public void deleteUsersById(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }
        Users users = userRepo.findById(userId)
                .orElseThrow(() ->  new NotFoundException( userId + " not found"));

        // Remove the user from all folders before deleting
        for (Folder folder : folderRepo.findAll()) {
            folder.getAllowedUsers().remove(users);
        }
        folderRepo.saveAll(folderRepo.findAll());

        // Remove the group from all folders before deleting
        for (DashBoard dashBoard : dashBoardRepo.findAll()) {
            dashBoard.getUsers().remove(users);
        }
        dashBoardRepo.saveAll(dashBoardRepo.findAll());

         userRepo.delete(users);
    }

    @Override
    @Transactional
    public Users addUserToGroup(Long userId, Long groupId) {
        Optional<Users> userOpt = userRepo.findById(userId);
        Optional<Groups> groupOpt = groupRepo.findById(groupId);

        if (userOpt.isPresent() && groupOpt.isPresent()) {
            Users user = userOpt.get();
            Groups group = groupOpt.get();

            user.getGroupMapped().add(group);
             return userRepo.save(user);
        } else {
            throw new NotFoundException("User or Group not found");
        }
    }

    @Override
    public List<Users> getAllUsers() {
        List<Users> users = userRepo.findAll();
        for(Users users1:users){
            // Extract group names into a Set<String>
            Set<String> groupNames = users1.getGroupMapped().stream()
                    .map(Groups::getGroupName) // get the groupName from each Group
                    .collect(Collectors.toSet()); // collect into a Set
            users1.setGroupName(groupNames);
        }

        return users;
    }

}
