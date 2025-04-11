package com.project.ITAM.Model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FolderRequest {
    private String folderName;
    private AccessType accessType;
    private Long parentFolderId;
    private FolderSourceType sourceType;
    private List<Long> userIds; // Only required for RESTRICTED folders
    private List<Long> groupIds; // Only required for RESTRICTED folders
    private List<Long> objectIds; // Only required for RESTRICTED folders
}
