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

    public Permission() {
    }

    public Permission(Long permissionId, String permissionName, String type) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.type = type;
    }
}
