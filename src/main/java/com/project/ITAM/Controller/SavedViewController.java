package com.project.ITAM.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ITAM.Model.SavedView;
import com.project.ITAM.Model.SavedViewRequest;
import com.project.ITAM.Service.SavedViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/view")
@CrossOrigin
@Validated
public class SavedViewController {

    @Autowired
    private SavedViewService savedViewService;

    /** save the view
     *
     * @param savedViewRequest
     * @return
     */
    @PostMapping("/saved")
    public ResponseEntity<SavedView> saveView(@RequestBody SavedViewRequest savedViewRequest) throws JsonProcessingException {
        return ResponseEntity.ok(savedViewService.saveView(savedViewRequest));
    }

    /** get view in folder
     *
     * @param folderId
     * @return
     */
    @GetMapping("/{folderId}/get")
    public ResponseEntity<List<SavedView>> getViewsInFolder(@PathVariable("folderId") Long folderId) {
        return ResponseEntity.ok(savedViewService.getViewssInFolder(folderId));
    }

    @GetMapping("/{viewId}/getView")
    public ResponseEntity<SavedViewRequest> getViewsById(@PathVariable("viewId") Long viewId) throws JsonProcessingException {
        return ResponseEntity.ok(savedViewService.getViewssById(viewId));
    }

    /** update by viewId
     *
     * @param viewId
     * @param savedViewRequest
     * @return
     */
    @PatchMapping("/{view_id}/update")
    public ResponseEntity<SavedView> updateViewName(@PathVariable("view_id") Long viewId,@RequestBody SavedViewRequest savedViewRequest) throws JsonProcessingException {
        return ResponseEntity.ok(savedViewService.updateView(viewId,savedViewRequest));
    }

    /** delete by view id
     *
     * @param viewId
     * @return
     */
    @DeleteMapping("/{view_id}/delete")
    public String deleteByViewId(@PathVariable("view_id") Long viewId){
        savedViewService.deleteView(viewId);
        return "file deleted";
    }

    /** get alla views
     *
     * @return
     */
    @GetMapping("/readAll")
    public ResponseEntity<List<SavedView>> getAllViews(){
        return  ResponseEntity.ok(savedViewService.getAllViews());
    }
}
