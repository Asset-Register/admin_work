package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name= "Permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    @Column(unique = true, nullable = false,name="permissionName")
    private String permissionName;

    @Column(name="type")
    private String type;

    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

    public Permission() {
    }

    public Permission(Long permissionId, String permissionName, String type, String createdBy, String updatedBy, String createdTime, String updatedTime) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.type = type;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
