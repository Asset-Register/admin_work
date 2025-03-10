package com.project.ITAM.Controller;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    /** create new dashBpoard
     *
     * @param dashBoard
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<DashBoard> uploadDashBoard(@RequestBody DashBoardRequest dashBoard) {
        return ResponseEntity.ok(dashBoardService.uploadDashBoard(dashBoard));
    }

    /** filter based on folder id
     *
     * @param folderId
     * @return
     */
    @GetMapping("/{folderId}/read")
    public ResponseEntity<List<DashBoard>> getDashBoardInnFolder(@PathVariable("folderId") Long folderId) {
        return ResponseEntity.ok(dashBoardService.getDashboardInFolder(folderId));
    }

    /** filter based on objectId
     *
     * @param objectId
     * @return
     */
    @GetMapping("/{object_id}/filter")
    public ResponseEntity<List<DashBoard>> getDashBoardbasedOnObjectId(@PathVariable("object_id") Long objectId) {
        return ResponseEntity.ok(dashBoardService.getDashboardBasedOnObjects(objectId));
    }

    @GetMapping("/{folderId}/withUniqueColumnsValues")
    public ResponseEntity<List<DashBoard>> getDashBoardInnFolderwithUniqueColumns(@PathVariable("folderId") Long folderId) {
        return ResponseEntity.ok(dashBoardService.getSelectedColumnValueDashBoard(folderId));
    }

    @PatchMapping("/{dashBoardId}/update")
    public ResponseEntity<DashBoard> updateFileName(@PathVariable("dashBoardId") Long dashBoardId,@RequestBody DashBoardRequest dashBoardRequest){
        return ResponseEntity.ok(dashBoardService.updatedashBoard(dashBoardId,dashBoardRequest));
    }

    @DeleteMapping("/{dashBoardId}/delete")
    public String deleteByFileId(@PathVariable("dashBoardId") Long dashBoardId){
        dashBoardService.deleteDashBoard(dashBoardId);
        return "dashBoard deleted";
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<DashBoard>> getAllDashboard(){
        return  ResponseEntity.ok(dashBoardService.getAllDashboard());
    }

}
