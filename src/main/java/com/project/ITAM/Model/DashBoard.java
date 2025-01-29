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

    private Long folderId; // Null if it's a root folder

    public DashBoard(Long id, String dashboardLink, Long folderId) {
        this.id = id;
        this.dashboardLink = dashboardLink;
        this.folderId = folderId;
    }
}

