package com.project.ITAM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "savedViews")
public class SavedView {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "viewName")
    private String viewName;

    @Column(name = "jobName")
    private String jobName;

    @Column(name="dataSource")
    private String dataSource;

    @Column(name="tableName")
    private String tableName;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @Column(columnDefinition = "TEXT",name = "filters" )
    private String filters; // JSON String

    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

    @Column(name="accessType")
    private AccessType accessType;

    @ManyToOne
    @JoinColumn(name = "objectId")
    private ObjectEntity object;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "saveView_users",
            joinColumns = @JoinColumn(name = "saveView_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<Users> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "saveView_group",
            joinColumns = @JoinColumn(name = "saveView_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonIgnore
    private Set<Groups> groups = new HashSet<>();
}
