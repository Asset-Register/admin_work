package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SavedViewRequest {

    private String viewName;

    private String jobName;

    private String dataSource;

    private Long folderId;

    private FilterRequest filters; // JSON String
}
