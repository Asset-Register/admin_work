package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "folder_access")
public class FolderAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;
}
