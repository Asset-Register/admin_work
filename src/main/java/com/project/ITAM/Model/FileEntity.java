package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="fileName")
    private String fileName;

    @Column(name="fileType")
    private String fileType;

    @Column(name="filePath")
    private String filePath; // Store actual file path

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder; // Folder where the file is stored

    public FileEntity() {
    }

    public FileEntity(Long id, String fileName, String fileType, String filePath, Folder folder) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.folder = folder;
    }
}

