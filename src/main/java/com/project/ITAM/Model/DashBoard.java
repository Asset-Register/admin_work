package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "DashBoard")
public class DashBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="dashBoardName")
    private String dashBoardName;

    @Column(name="accessType")
    private  FolderType accessType;

    @ManyToOne
    @JoinColumn(name = "objectId")
    private ObjectEntity object;

    @Lob
    @Column(name="description")
    private String description;

    @Column(name="dashboardType")
    private String dashboardType;

    @Column(name="sourceName")
    private String sourceName;

    @Column(name="tableName")
    private String tableName;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder; // Folder where the file is stored

    public DashBoard() {
    }

    public DashBoard(Long id, String dashBoardName, FolderType accessType, ObjectEntity object, String description, String dashboardType, String sourceName, String tableName, Folder folder) {
        this.id = id;
        this.dashBoardName = dashBoardName;
        this.accessType = accessType;
        this.object = object;
        this.description = description;
        this.dashboardType = dashboardType;
        this.sourceName = sourceName;
        this.tableName = tableName;
        this.folder = folder;
    }
}

