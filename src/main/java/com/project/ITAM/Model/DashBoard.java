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

    private String dashboardLink;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder; // Folder where the file is stored

    public DashBoard() {
    }

    public DashBoard(Long id, String dashboardLink, Folder folder) {
        this.id = id;
        this.dashboardLink = dashboardLink;
        this.folder = folder;
    }
}

