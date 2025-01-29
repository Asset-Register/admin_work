package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Builder
@Data
@Table(name= "Groups")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(nullable = false,name="groupName")
    private String groupName;

  /*  @OneToMany(mappedBy = "group", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Users> users;*/

    @Column(name="userId")
    private Long userId;

    @Column(name="email")
    private String email;

    @Column(name="roleId")
    private String roleId;

    @Column(name="objects")
    private String objects;

    @Column(name="disabled")
    private String disabled;

    @Column(name="authentication")
    private String authentication;

    public Groups() {
    }

    public Groups(Long groupId, String groupName, Long userId, String email, String roleId, String objects, String disabled, String authentication) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.userId = userId;
        this.email = email;
        this.roleId = roleId;
        this.objects = objects;
        this.disabled = disabled;
        this.authentication = authentication;
    }
}
