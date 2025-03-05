package com.project.ITAM.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ITAM-Data", url = "${itam.data.url}")
public interface ITAMClient {

    @GetMapping("/{tableName}/getColumnsValues/{columnNames}")
    public List<Map<String, Object>> getColumnValues(@PathVariable("tableName") String tableName, @PathVariable("columnNames") String columnNames);
}
