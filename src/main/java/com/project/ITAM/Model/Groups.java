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

    @ManyToMany(mappedBy = "allowedGroups")
    private Set<Folder> accessibleFolders = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    private Set<Users> users = new HashSet<>();

    public Groups() {
    }

    public Groups(Long groupId, String groupName, String email, Set<ObjectEntity> objects, String disabled, String authentication, Set<Folder> accessibleFolders, Set<Users> users) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.email = email;
        this.objectEntities = objects;
        this.disabled = disabled;
        this.authentication = authentication;
        this.accessibleFolders = accessibleFolders;
        this.users = users;
    }
}
