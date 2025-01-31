package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.GroupRequest;
import com.project.ITAM.Model.Groups;
import com.project.ITAM.Repository.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GroupsServiceImpl implements GroupsService{

    @Autowired
    private GroupRepo groupRepo;

    @Override
    public Groups createGroups(GroupRequest groupRequest) {
        return groupRepo.save(Groups.builder().groupName(groupRequest.getGroupName()).authentication(groupRequest.getAuthentication())
                .disabled(groupRequest.getDisabled()).email(groupRequest.getEmail()).objects(groupRequest.getObjects()).build());
    }

    @Override
    public Groups getGroupById(Long groupId) {
        Groups group = groupRepo.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        return group;
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
        if(!StringUtils.isEmpty(groupRequest.getObjects())) {
            groups.setObjects(groupRequest.getObjects());
        }
        if(!StringUtils.isEmpty(groupRequest.getEmail())) {
            groups.setEmail(groupRequest.getEmail());
        }
        if(!StringUtils.isEmpty(groupRequest.getDisabled())) {
            groups.setDisabled(groupRequest.getDisabled());
        }

        return groupRepo.save(groups);
    }

    @Override
    public void deleteGroupById(Long groupId) {
        if (!groupRepo.existsById(groupId)) {
            throw new NotFoundException("group with ID " + groupId + " not found");
        }
        groupRepo.deleteById(groupId);
    }
}
