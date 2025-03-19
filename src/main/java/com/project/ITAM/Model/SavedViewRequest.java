package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class SavedViewRequest {

    @NonNull
    private String viewName;

    private String jobName;

    private String dataSource;

    @NonNull
    private String tableName;

    private Long folderId;

    private FilterRequest filters; // JSON String

    private AccessType accessType;
    private List<Long> userIds; // Only required for RESTRICTED folders
    private List<Long> groupIds; // Only required for RESTRICTED folders
}
