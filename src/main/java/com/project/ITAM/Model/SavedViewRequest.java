package com.project.ITAM.Model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class SavedViewRequest {

    @NotNull(message = "viewName is mandatory")
    private String viewName;

    private String jobName;

    private String dataSource;

    @NotNull(message = "tableName is mandatory")
    private String tableName;

    private Long folderId;

    private FilterRequest filters; // JSON String

    private AccessType accessType;
    private List<Long> userIds; // Only required for RESTRICTED folders
    private List<Long> groupIds; // Only required for RESTRICTED folders
}
