package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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

    @Column(name="email")
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "groups_object",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "object_id")
    )
    private Set<ObjectEntity> objectEntities = new HashSet<>();

    @Column(name="disabled")
    private String disabled;

    @Column(name="authentication")
    private String authentication;

    @ManyToMany(mappedBy = "allowedGroups",cascade = CascadeType.ALL)
    private Set<Folder> accessibleFolders = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    private Set<Users> users = new HashSet<>();

    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

    public Groups() {
    }

    public Groups(Long groupId, String groupName, String email, Set<ObjectEntity> objectEntities, String disabled, String authentication, Set<Folder> accessibleFolders, Set<Users> users, String createdBy, String updatedBy, String createdTime, String updatedTime) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.email = email;
        this.objectEntities = objectEntities;
        this.disabled = disabled;
        this.authentication = authentication;
        this.accessibleFolders = accessibleFolders;
        this.users = users;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
