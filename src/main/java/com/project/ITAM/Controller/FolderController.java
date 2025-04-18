package com.project.ITAM.Controller;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.FolderDTO;
import com.project.ITAM.Model.FolderRequest;
import com.project.ITAM.Repository.FileRepo;
import com.project.ITAM.Repository.FolderRepo;
import com.project.ITAM.Service.FolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createFolder(@RequestBody FolderRequest folder, @RequestHeader("userId") Long userId) {
        try {
            logger.info("Create Folder By userId:" + folder.getFolderName() +" : "+ userId);
            Folder created = folderService.createFolder(folder, userId);
            return ResponseEntity.ok(created);
        } catch (Exception ex) {
            logger.error("Error creating folder: " + ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    /** get all folder
     *
     * @return
     */
    @GetMapping("/getAll/{sourceType}")
    public ResponseEntity<?> getAllFolders(@PathVariable("sourceType") String sourceType) {
        try {
            List<FolderDTO> folders = folderService.getAllFolders(sourceType);
            return ResponseEntity.ok(folders);
        } catch (Exception ex) {
            logger.error("Error fetching folders for sourceType: " + sourceType, ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch folders", "details", ex.getMessage()));
        }
    }


    /** update folder based on folderId
     *
     * @param folderId
     * @param folderRequest
     * @return
     */
    @PatchMapping("/{folderId}/update")
    public ResponseEntity<?> updateFolders(@PathVariable Long folderId, @RequestBody FolderRequest folderRequest) {
        try {
            Folder folder = folderService.updateFolder(folderRequest, folderId);
            return ResponseEntity.ok(folder);
        } catch (NotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update folder", "details", ex.getMessage()));
        }
    }


    /** get root folders
     *
     * @return
     */
    @GetMapping("/getfolder/{sourceType}/root")
    public ResponseEntity<?> getRootFolders(@PathVariable("sourceType") String sourceType) {
        try {
            List<FolderDTO> folders = folderService.getParentFolders(sourceType);
            logger.info("Root Folders: " + folders);
            return ResponseEntity.ok(folders);
        } catch (NotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            logger.error("Error fetching root folders for sourceType: " + sourceType, ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch root folders", "details", ex.getMessage()));
        }
    }


    /** get Folder details by folder id
     *
     * @param folderId
     * @return
     */
    @GetMapping("/{folderId}/folder/{sourceType}")
    public ResponseEntity<?> getFolderById(@PathVariable Long folderId, @PathVariable("sourceType") String sourceType) {
        try {
            FolderDTO folder = folderService.getFolderById(folderId, sourceType);
            logger.info("Get Folder By Id: " + folder);
            return ResponseEntity.ok(folder);
        } catch (NotFoundException ex) {
            logger.error("Folder not found: " + folderId + ", sourceType: " + sourceType, ex);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            logger.error("Error retrieving folder by id", ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve folder", "details", ex.getMessage()));
        }
    }


    /**  user can access the all Public folders and Only the owner and allowed users
     *
      * @param userId
     * @return
     */
    @GetMapping("/{userId}/user/{sourceType}")
    public ResponseEntity<?> getFolderByUserId(@PathVariable Long userId, @PathVariable("sourceType") String sourceType) {
        try {
            List<FolderDTO> folders = folderService.getFolderByUserId(userId, sourceType);
            logger.info("Get Folders By UserId: " + folders);
            return ResponseEntity.ok(folders);
        } catch (NotFoundException ex) {
            logger.error("User not found: " + userId, ex);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            logger.error("Error fetching folders by userId", ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve folders", "details", ex.getMessage()));
        }
    }


    /** public all allowed
     * RESTRICTED :If user is in folder_access / folder_group_access / folder_object_access
     * PRIVATE: Only if f.user_id = :userId (the owner)
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/all/{sourceType}")
    public ResponseEntity<?> getAllUserIDFolders(@PathVariable Long userId, @PathVariable("sourceType") String sourceType) {
        try {
            List<FolderDTO> folder = folderService.getFolderByUserIdANDGroupID(userId, sourceType);
            logger.info("Get ALL Folders Related to UserId: " + folder);
            return ResponseEntity.ok(folder);
        } catch (NotFoundException ex) {
            logger.error("User not found: " + userId, ex);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            logger.error("Error retrieving folders for userId: " + userId, ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve folders", "details", ex.getMessage()));
        }
    }


    @DeleteMapping("/{folderId}/delete/{sourceType}")
    public ResponseEntity<?> deleteByFolderId(@PathVariable Long folderId, @PathVariable("sourceType") String sourceType) {
        try {
            folderService.deleteByFolderId(folderId, sourceType);
            return ResponseEntity.ok(Map.of("message", "Folder deleted successfully"));
        } catch (NotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete folder", "details", ex.getMessage()));
        }
    }


}
