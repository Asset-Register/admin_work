package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SavedViewRequest {

    private String viewName;

    private String jobName;

    private String dataSource;

    private Long folderId;

    private FilterRequest filters; // JSON String

    private AccessType accessType;
    private List<Long> userIds; // Only required for RESTRICTED folders
    private List<Long> groupIds; // Only required for RESTRICTED folders
}
