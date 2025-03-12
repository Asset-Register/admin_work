package com.project.ITAM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSchedule {

    private String jobName;

    private String createdSTP;

    private String createdBy;

    private String updatedBy;

    @JsonIgnore
    private String cronexpression;

    private String updatedSTP;

    private String jobType;

    private LocalDateTime lastExecutionDate;

    private LocalDateTime nextExecutionDate;

    private String status;

    private String DCTableName;

    private String ACTableName;

    @JsonIgnore
    private int numberOfJobs;

    @JsonIgnore
    private String jsonData;

    @JsonIgnore
    private byte[] googleCloudFile;

    private String dataConsole;

    private String disable;

    private Long object;

}
