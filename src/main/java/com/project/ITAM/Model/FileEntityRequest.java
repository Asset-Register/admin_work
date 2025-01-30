package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileEntityRequest {

    private String fileName;
    private String fileType;
    private String filePath; // Store actual file path

    private Long folderId; // Folder where the file is stored

    public FileEntityRequest() {
    }

    public FileEntityRequest(String fileName, String fileType, String filePath, Long folderId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.folderId = folderId;
    }
}

