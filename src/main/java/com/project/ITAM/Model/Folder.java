package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "folders")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String folderName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parentFolder; // Null if it's a root folder

    private String parentType;// "private" or "public"

    private Long dashboardId;

    public Folder() {
    }

    public Folder(Long id, String folderName, Folder parentFolder, String parentType, Long dashboardId) {
        this.id = id;
        this.folderName = folderName;
        this.parentFolder = parentFolder;
        this.parentType = parentType;
        this.dashboardId = dashboardId;
    }
}

