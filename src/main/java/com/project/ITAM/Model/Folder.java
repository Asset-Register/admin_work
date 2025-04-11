package com.project.ITAM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "folders")
@ToString(exclude = {"parentFolder", "childFolders", "allowedUsers", "allowedGroups", "allowedObjects"})
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name="folderName")
    private String folderName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Folder parentFolder; // Null if it's a root folder

    @Enumerated(EnumType.STRING)
    @Column(name= "folderType")
    private AccessType accessType;// "private" or "public" or "Restricted"

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "folder_access",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Users> allowedUsers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "folder_group_access",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Groups> allowedGroups = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "folder_object_access",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "object_id")
    )
    private Set<ObjectEntity> allowedObjects = new HashSet<>();

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Prevent recursion when serializing
    private List<Folder> childFolders = new ArrayList<>();

    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

}

