package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@Table(name= "ObjectEntity")
public class ObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectId;

    @Column(nullable = false,name="objectName")
    private String objectName;

    @Column(name="email")
    private String email;

    @ManyToMany(mappedBy = "allowedObjects")
    private Set<Folder> accessibleFolders = new HashSet<>();

    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

    public ObjectEntity() {
    }

    public ObjectEntity(Long objectId, String objectName, String email, Set<Folder> accessibleFolders, String createdBy, String updatedBy, String createdTime, String updatedTime) {
        this.objectId = objectId;
        this.objectName = objectName;
        this.email = email;
        this.accessibleFolders = accessibleFolders;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
