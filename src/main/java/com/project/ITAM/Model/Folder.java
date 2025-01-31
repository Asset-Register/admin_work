package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@Table(name = "folders")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="folderName")
    private String folderName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parentFolder; // Null if it's a root folder

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FolderType folderType;// "private" or "public" or "Restricted"

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "folder_access",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Users> allowedUsers;

    @ManyToMany
    @JoinTable(
            name = "folder_group_access",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Groups> allowedGroups = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "folder_object_access",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "object_id")
    )
    private Set<ObjectEntity> allowedObjects = new HashSet<>();


    public Folder() {
    }

    public Folder(Long id, String folderName, Folder parentFolder, FolderType folderType, Users user, Set<Users> allowedUsers, Set<Groups> allowedGroups, Set<ObjectEntity> allowedObjects) {
        this.id = id;
        this.folderName = folderName;
        this.parentFolder = parentFolder;
        this.folderType = folderType;
        this.user = user;
        this.allowedUsers = allowedUsers;
        this.allowedGroups = allowedGroups;
        this.allowedObjects = allowedObjects;
    }
}

