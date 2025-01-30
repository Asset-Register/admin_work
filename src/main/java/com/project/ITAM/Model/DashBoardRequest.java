package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DashBoardRequest {
    private String dashboardLink;

    private Long folderId; // Folder where the file is stored

    public DashBoardRequest() {
    }

    public DashBoardRequest(String dashboardLink, Long folderId) {
        this.dashboardLink = dashboardLink;
        this.folderId = folderId;
    }
}

