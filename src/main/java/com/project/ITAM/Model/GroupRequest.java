package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GroupRequest {

    private String groupName;

    private String email;

    private List<Long> objectId;

    private String disabled;

    private String authentication;

    public GroupRequest() {
    }

    public GroupRequest(String groupName, String email, List<Long> objects, String disabled, String authentication) {
        this.groupName = groupName;
        this.email = email;
        this.objectId = objects;
        this.disabled = disabled;
        this.authentication = authentication;
    }
}
