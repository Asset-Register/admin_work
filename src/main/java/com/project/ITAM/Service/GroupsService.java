package com.project.ITAM.Service;

import com.project.ITAM.Model.GroupRequest;
import com.project.ITAM.Model.Groups;

public interface GroupsService {
    public Groups createGroups(GroupRequest groupRequest);

    public Groups getGroupById(Long groupId);

    public Groups updateGroupById(GroupRequest groupRequest,Long groupId);

    public void deleteGroupById(Long groupId);

}
