package com.project.ITAM.Model;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class FolderRequest {
    private String folderName;
    private FolderType folderType;
    private Long parentFolderId;
    private List<Long> userIds; // Only required for RESTRICTED folders
    private Long groupId; // Only required for RESTRICTED folders
    private String object; // Only required for RESTRICTED folders
}
