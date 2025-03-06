package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
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

    public SavedView() {
    }

    public SavedView(Long id, String viewName, String jobName, String dataSource, Folder folder, String filters, String createdBy, String updatedBy, String createdTime, String updatedTime) {
        this.id = id;
        this.viewName = viewName;
        this.jobName = jobName;
        this.dataSource = dataSource;
        this.folder = folder;
        this.filters = filters;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
