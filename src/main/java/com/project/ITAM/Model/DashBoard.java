package com.project.ITAM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DashBoard")
public class DashBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "dashboard_users",
            joinColumns = @JoinColumn(name = "dashboard_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<Users> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "dashboard_group",
            joinColumns = @JoinColumn(name = "dashboard_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonIgnore
    private Set<Groups> groups = new HashSet<>();


    @Column(name="dashBoardName")
    private String dashBoardName;

    @Column(name="accessType")
    private AccessType accessType;

    @ManyToOne
    @JoinColumn(name = "objectId")
    private ObjectEntity object;

    @Lob
    @Column(name="description")
    private String description;

    @Column(name="chartType")
    private String chartType;

    @Column(name="sourceType")
    private String sourceType;

    @Column(columnDefinition = "TEXT") // Stores the List<String> as JSON string
    @JsonIgnore
    private String tableNamesJson;

    @Transient
    private List<String> tableNames = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonIgnore
    private Folder folder; // Folder where the file is stored

    @Column(name="createdBy")
    private String createdBy;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name="createdTime")
    private String createdTime;

    @Column(name="updatedTime")
    private String updatedTime;

    @Column(columnDefinition = "TEXT") // Store JSON string
    @JsonIgnore
    private String columnNamesJson;

    @Transient
    private Map<String, List<String>> columnNames = new HashMap<>();

    @Transient
    private Map<Map<String, Object>, Long> columnNamesWithValuesANDCounting = new HashMap<>();

    // Convert JSON string to Map after loading from DB
    @PostLoad
    private void loadColumnNames() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.columnNames = objectMapper.readValue(this.columnNamesJson, new TypeReference<Map<String, List<String>>>() {});
            this.tableNames = objectMapper.readValue(this.tableNamesJson, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            this.columnNames = new HashMap<>();
            this.tableNames = new ArrayList<>();
        }
    }

    // Convert Map to JSON string before saving to DB
    @PrePersist
    @PreUpdate
    private void saveColumnNames() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.columnNamesJson = objectMapper.writeValueAsString(this.columnNames);
            this.tableNamesJson = objectMapper.writeValueAsString(this.tableNames);
        } catch (IOException e) {
            this.columnNamesJson = "{}";
            this.tableNamesJson = "[]"; // Default to empty JSON array
        }
    }


}

