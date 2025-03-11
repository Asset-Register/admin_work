package com.project.ITAM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "ObjectEntity")
public class ObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectId;

    @Column(nullable = false,name="objectName")
    private String objectName;

    @Column(name="email")
    private String email;

    @ManyToMany(mappedBy = "allowedObjects")
    @JsonIgnore
    private Set<Folder> accessibleFolders = new HashSet<>();

    @Column(name="createdBy")
    @JsonIgnore
    private String createdBy;

    @Column(name="updatedBy")
    @JsonIgnore
    private String updatedBy;

    @Column(name="createdTime")
    @JsonIgnore
    private String createdTime;

    @Column(name="updatedTime")
    @JsonIgnore
    private String updatedTime;
}
