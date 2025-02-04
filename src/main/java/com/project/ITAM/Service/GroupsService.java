package com.project.ITAM.Service;

import com.project.ITAM.Model.GroupRequest;
import com.project.ITAM.Model.Groups;

import java.util.List;

public interface GroupsService {
    public Groups createGroups(GroupRequest groupRequest);

    public Groups getGroupById(Long groupId);

    public List<Groups> getAllGroups();

    public Groups updateGroupById(GroupRequest groupRequest,Long groupId);

    public void deleteGroupById(Long groupId);

}
