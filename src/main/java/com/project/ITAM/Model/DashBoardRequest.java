package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class DashBoardRequest {
    private String dashboardName;

    private Long folderId;// Folder where the file is stored

    private Long objectId;

    private FolderType folderType;

    private String description;

    private String chartType;

    private Map<String,List<String>> columnNames;

    private List<String> tableName;

    private List<Long> userIds; // Only required for RESTRICTED folders
    private List<Long> groupIds; // Only required for RESTRICTED folders
}

