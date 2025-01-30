package com.project.ITAM.Controller;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Service.DashBoardService;
import com.project.ITAM.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dashboard")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @PostMapping("/upload")
    public ResponseEntity<DashBoard> uploadDashBoard(@RequestBody DashBoardRequest dashBoard) {
        return ResponseEntity.ok(dashBoardService.uploadFile(dashBoard));
    }

    @GetMapping("/{folderId}/dashboard")
    public ResponseEntity<List<DashBoard>> getDashBoardInnFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(dashBoardService.getDashboardInFolder(folderId));
    }
}
