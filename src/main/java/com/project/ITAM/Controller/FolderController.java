package com.project.ITAM.Controller;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.FolderDTO;
import com.project.ITAM.Model.FolderRequest;
import com.project.ITAM.Repository.FileRepo;
import com.project.ITAM.Repository.FolderRepo;
import com.project.ITAM.Service.FolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/folder")
@CrossOrigin
@Validated
public class FolderController {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private FileRepo fileRepo;

    @Autowired
            private FolderService folderService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /** create folder
     *
     * @param folder
     * @param userId
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Folder> createFolder(@RequestBody FolderRequest folder, @RequestHeader("userId") Long userId) {
        logger.info("Create Folder By userId:" + folder.getFolderName() +" : "+ userId);
        return ResponseEntity.ok(folderService.createFolder(folder,userId));
    }

    /** get all folder
     *
     * @return
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<FolderDTO>> getAllFolders() {
        logger.info("Number of folders:" + folderService.getAllFolders().size());
        return ResponseEntity.ok(folderService.getAllFolders());
    }

    /** update folder based on folderId
     *
     * @param folderId
     * @param folderRequest
     * @return
     */
    @PatchMapping("/{folderId}/update")
    public ResponseEntity<Folder> updateFolders(@PathVariable Long folderId,@RequestBody FolderRequest folderRequest) {
        Folder folder = folderService.updateFolder(folderRequest,folderId);
        return ResponseEntity.ok(folder);
    }

    /** get root folders
     *
     * @return
     */
    @GetMapping("/getfolder/root")
    public ResponseEntity<List<FolderDTO>> getRootFolders() {
        logger.info("Root Folders:" + folderService.getParentFolders());
        return ResponseEntity.ok(folderService.getParentFolders());
    }

    /** get Folder details by folder id
     *
     * @param folderId
     * @return
     */
    @GetMapping("/{folderId}")
    public ResponseEntity<FolderDTO> getFolderById(@PathVariable Long folderId) {
        FolderDTO folder = folderService.getFolderById(folderId);
        logger.info("Get Folder By Id:" + folder);
        return ResponseEntity.ok(folder);
    }

    /**  user can access the all Public folders and Only the owner and allowed users
     *
      * @param userId
     * @return
     */
    @GetMapping("/{userId}/user")
    public ResponseEntity<List<FolderDTO>> getFolderByUserId(@PathVariable Long userId) {
        List<FolderDTO> folder = folderService.getFolderByUserId(userId);
        logger.info("Get Folders By UserId:" + folder);
        return ResponseEntity.ok(folder);
    }

    /** public all allowed
     * RESTRICTED :If user is in folder_access / folder_group_access / folder_object_access
     * PRIVATE: Only if f.user_id = :userId (the owner)
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/all")
    public ResponseEntity<List<FolderDTO>> getAllUserIDFolders(@PathVariable Long userId) {
        List<FolderDTO> folder = folderService.getFolderByUserIdANDGroupID(userId);
        logger.info("Get ALL Folders Related to UserId:" + folder);
        return ResponseEntity.ok(folder);
    }

    @DeleteMapping("/{folderId}/delete")
    public String deleteByFolderId(@PathVariable Long folderId){
        folderService.deleteByFolderId(folderId);
        return "folder deleted";
    }

}
