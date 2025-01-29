package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    /*@OneToMany(mappedBy = "permission", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Role> roles;*/

    @Column(name="roleId")
    private long roleId;

    @Column(name="type")
    private String type;

    public Permission() {
    }

    public Permission(Long permissionId, String permissionName, long roleId, String type) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.roleId = roleId;
        this.type = type;
    }
}
