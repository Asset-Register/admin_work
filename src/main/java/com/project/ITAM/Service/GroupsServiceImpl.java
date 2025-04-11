package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.*;
import com.project.ITAM.helper.DateTimeUtil;
import com.project.ITAM.helper.ExtractJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupsServiceImpl implements GroupsService{

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private ObjectRepo objectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
            private DashBoardRepo dashBoardRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Groups createGroups(GroupRequest groupRequest) {
        Set<ObjectEntity> objectEntity = new HashSet<>();
        if( !CollectionUtils.isEmpty(groupRequest.getObjectId())) {
            // Fetch groups from the database
            objectEntity = new HashSet<>(objectRepo.findAllById(groupRequest.getObjectId()));
            if(objectEntity.isEmpty()){
                throw new NotFoundException("entered objectId not exist");
            }
        }

        return groupRepo.save(Groups.builder().groupName(groupRequest.getGroupName()).createdBy(ExtractJsonUtil.getUserdetails())
                .createdTime(DateTimeUtil.currentDateTime())
                .authentication(groupRequest.getAuthentication())
                .disabled(groupRequest.getDisabled()).email(groupRequest.getEmail()).objectEntities(objectEntity).build());
    }

    @Override
    public Groups getGroupById(Long groupId) {
        Groups group = groupRepo.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        return group;
    }

    @Override
    public List<Groups> getAllGroups() {
        return groupRepo.findAll();
    }

    @Override
    public Groups updateGroupById(GroupRequest groupRequest, Long groupId) {
        Groups groups = groupRepo.findById(groupId)
                .orElseThrow(() ->  new NotFoundException( + groupId + " not found"));

        if(!StringUtils.isEmpty(groupRequest.getAuthentication())) {
            groups.setAuthentication(groupRequest.getAuthentication());
        }
        if(!StringUtils.isEmpty(groupRequest.getGroupName())) {
            groups.setGroupName(groupRequest.getGroupName());
        }
        if( !CollectionUtils.isEmpty(groupRequest.getObjectId())) {
            // Fetch groups from the database
            Set<ObjectEntity> objectEntity = new HashSet<>(objectRepo.findAllById(groupRequest.getObjectId()));
            if(!objectEntity.isEmpty()){
                groups.setObjectEntities(objectEntity);
            }else{
                throw new NotFoundException("object id not found");
            }
        }
        if(!StringUtils.isEmpty(groupRequest.getEmail())) {
            groups.setEmail(groupRequest.getEmail());
        }
        if(!StringUtils.isEmpty(groupRequest.getDisabled())) {
            groups.setDisabled(groupRequest.getDisabled());
        }
         groups.setUpdatedBy(ExtractJsonUtil.getUserdetails());
        groups.setUpdatedTime(DateTimeUtil.currentDateTime());
        return groupRepo.save(groups);
    }

    @Override
    public void deleteGroupById(Long groupId) {
        if (!groupRepo.existsById(groupId)) {
            throw new NotFoundException("group with ID " + groupId + " not found");
        }
        Groups groups = groupRepo.findById(groupId)
                .orElseThrow(() ->  new NotFoundException( + groupId + " not found"));
        // Remove the group from all users before deleting
        for (Users user : userRepo.findAll()) {
            user.getGroupMapped().remove(groups);
        }
        userRepo.saveAll(userRepo.findAll());

        // Remove the group from all folders before deleting
        for (Folder folder : folderRepo.findAll()) {
            folder.getAllowedGroups().remove(groups);
        }
        folderRepo.saveAll(folderRepo.findAll());

        // Remove the group from all folders before deleting
        for (DashBoard dashBoard : dashBoardRepo.findAll()) {
            dashBoard.getGroups().remove(groups);
        }
        dashBoardRepo.saveAll(dashBoardRepo.findAll());

        groupRepo.deleteById(groupId);
    }
}
