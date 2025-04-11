package com.project.ITAM.Controller;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/data/dashboard")
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

    /**
     * Controller to grouping the particular Dashboard with combination of column unique records
     * in particular folder get the dashboards
     * Each dashboard get the columnNames from Table
     * From itamClient api we get the value of columnNames selected  for particular table
     * group the unique records key (columnname and value ) and value -> counting of unique values
     *
     * @param folderId
     * @return
     */
    @GetMapping("/{folderId}/withUniqueColumnsValues")
    public ResponseEntity<List<DashBoard>> getDashBoardInnFolderwithUniqueColumns(@PathVariable("folderId") Long folderId) {
        return ResponseEntity.ok(dashBoardService.getSelectedColumnValueDashBoard(folderId));
    }

    /**
     * dashBoardname should be unique with in same objectId and folderId
     *
     * @param folderId
     * @param objectId
     * @param dashboardName
     * @return
     */
    @GetMapping("/{folderId}/folder/{objectId}/object/{dashboardName}/dashboardName")
    public ResponseEntity<String> checkUniqueDashBoardNamewithSameObjectandFolderId(@PathVariable("folderId") Long folderId,@PathVariable("objectId") Long objectId,@PathVariable("dashboardName") String dashboardName) {
        boolean valid =dashBoardService.checkdashBoardUniqueNameInSameObjectandFolder(folderId,objectId,dashboardName);
        if(valid){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "dashBoardName should be Unique");
        }else{
            return ResponseEntity.ok("valid dashBoardName");
        }
    }

    /**
     * update dashboard details
     * @param dashBoardId
     * @param dashBoardRequest
     * @return
     */
    @PatchMapping("/{dashBoardId}/update")
    public ResponseEntity<DashBoard> updateFileName(@PathVariable("dashBoardId") Long dashBoardId,@RequestBody DashBoardRequest dashBoardRequest){
        return ResponseEntity.ok(dashBoardService.updatedashBoard(dashBoardId,dashBoardRequest));
    }

    /**
     * delete the dashBoard
     * @param dashBoardId
     * @return
     */
    @DeleteMapping("/{dashBoardId}/delete")
    public String deleteByFileId(@PathVariable("dashBoardId") Long dashBoardId){
        dashBoardService.deleteDashBoard(dashBoardId);
        return "dashBoard deleted";
    }

    /**
     * get all dashBoards
     * @return
     */
    @GetMapping("/readAll")
    public ResponseEntity<List<DashBoard>> getAllDashboard(){
        return  ResponseEntity.ok(dashBoardService.getAllDashboard());
    }

}
