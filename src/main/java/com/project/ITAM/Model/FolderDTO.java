package com.project.ITAM.Model;

import lombok.Data;

import java.util.List;

@Data
public class FolderDTO {
    private Long id;
    private String folderName;
    private List<FolderDTO> childFolders;
    private Long userId;
    private AccessType accessType;

    public FolderDTO(Long id, String folderName, List<FolderDTO> childFolders, Long userId, AccessType accessType) {
        this.id = id;
        this.folderName = folderName;
        this.childFolders = childFolders;
        this.userId = userId;
        this.accessType = accessType;
    }
}
