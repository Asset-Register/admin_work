package com.project.ITAM.client;

import com.project.ITAM.Model.JobSchedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@FeignClient(name = "admin-console", url = "${itam.data.url}")
public interface ITAMClient {

    @GetMapping("/table/{tableName}/getColumnsValues")
    public List<Map<String, Object>> getColumnValues(@PathVariable("tableName") String tableName, @RequestParam("columnNames") List<String> columnNames);

    @GetMapping("/jobSchedule/{jobName}/view")
    public Optional<JobSchedule> viewJobs(@PathVariable String jobName);
}
