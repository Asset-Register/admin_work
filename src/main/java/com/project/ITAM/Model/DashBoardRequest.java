package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DashBoardRequest {
    private String dashboardName;

    private Long folderId;// Folder where the file is stored

    private Long objectId;

    private FolderType accessType;

    private String description;

    private String dashBoardType;

    private String sourceName;

    private String tableName;

    public DashBoardRequest() {
    }

    public DashBoardRequest(String dashboardName, Long folderId, Long objectId, FolderType accessType, String description, String dashBoardType, String sourceName, String tableName) {
        this.dashboardName = dashboardName;
        this.folderId = folderId;
        this.objectId = objectId;
        this.accessType = accessType;
        this.description = description;
        this.dashBoardType = dashBoardType;
        this.sourceName = sourceName;
        this.tableName = tableName;
    }
}

