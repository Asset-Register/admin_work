package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@Table(name= "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @Column(nullable = false,name="roleName")
    private String roleName;

    @Column(name="disabled")
    private String disabled;

    public Role() {
    }

    public Role(Long roleId, Set<Permission> permission, String roleName, String disabled) {
        this.roleId = roleId;
        this.permissions = permission;
        this.roleName = roleName;
        this.disabled = disabled;
    }
}
