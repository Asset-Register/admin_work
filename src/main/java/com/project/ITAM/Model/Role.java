package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(name= "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "permissionId", nullable = false) // Foreign key in the users table
    private Permission permission;

    @Column(nullable = false,name="roleName")
    private String roleName;

    @Column(name="disabled")
    private String disabled;

    public Role() {
    }

    public Role(Long roleId, Permission permission, String roleName, String disabled) {
        this.roleId = roleId;
        this.permission = permission;
        this.roleName = roleName;
        this.disabled = disabled;
    }
}
