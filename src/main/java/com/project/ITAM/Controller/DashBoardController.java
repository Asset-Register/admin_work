package com.project.ITAM.Controller;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Service.DashBoardService;
import com.project.ITAM.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin
@Validated
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @PostMapping("/upload")
    public ResponseEntity<DashBoard> uploadDashBoard(@RequestBody DashBoardRequest dashBoard) {
        return ResponseEntity.ok(dashBoardService.uploadDashBoard(dashBoard));
    }

    @GetMapping("/{folderId}/dashboard")
    public ResponseEntity<List<DashBoard>> getDashBoardInnFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(dashBoardService.getDashboardInFolder(folderId));
    }

    @PatchMapping("/{dashBoardId}/update")
    public ResponseEntity<DashBoard> updateFileName(@PathVariable Long dashBoardId,@RequestBody DashBoardRequest dashBoardRequest){
        return ResponseEntity.ok(dashBoardService.updatedashBoard(dashBoardId,dashBoardRequest));
    }

    @DeleteMapping("/{dashBoardId}/delete")
    public String deleteByFileId(@PathVariable Long dashBoardId){
        dashBoardService.deleteDashBoard(dashBoardId);
        return "dashBoard deleted";
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<DashBoard>> getAllDashboard(){
        return  ResponseEntity.ok(dashBoardService.getAllDashboard());
    }

}
