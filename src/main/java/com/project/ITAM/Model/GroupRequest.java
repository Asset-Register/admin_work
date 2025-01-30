package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GroupRequest {

    private String groupName;

    private String email;

    private String objects;

    private String disabled;

    private String authentication;

    public GroupRequest() {
    }

    public GroupRequest(String groupName, String email, String objects, String disabled, String authentication) {
        this.groupName = groupName;
        this.email = email;
        this.objects = objects;
        this.disabled = disabled;
        this.authentication = authentication;
    }
}
