package com.project.ITAM.Controller;

import com.project.ITAM.Model.Folder;
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
@RequestMapping("/api/folder")
@Validated
public class FolderController {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private FileRepo fileRepo;

    @Autowired
            private FolderService folderService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/create")
    public ResponseEntity<Folder> createFolder(@RequestBody FolderRequest folder, @RequestHeader("userId") Long userId) {
        logger.info("Create Folder By userId:" + folder.getFolderName() +" : "+ userId);
        return ResponseEntity.ok(folderService.createFolder(folder,userId));
    }

    @GetMapping("/getAllfolders")
    public ResponseEntity<List<Folder>> getAllFolders() {
        logger.info("Number of folders:" + folderRepo.findAll().size());
        return ResponseEntity.ok(folderRepo.findAll());
    }

    @GetMapping("/getfolder/root")
    public ResponseEntity<List<Folder>> getRootFolders() {
        logger.info("Root Folders:" + folderRepo.findByParentFolderIsNull());
        return ResponseEntity.ok(folderRepo.findByParentFolderIsNull());
    }

    @GetMapping("/{folderId}/folder")
    public ResponseEntity<Folder> getFolderById(@PathVariable Long folderId) {
        Folder folder = folderService.getFolderById(folderId);
        logger.info("Get Folder By Id:" + folder);
        return ResponseEntity.ok(folder);
    }

    @GetMapping("/{userId}/folder")
    public ResponseEntity<List<Folder>> getFolderByUserId(@PathVariable Long userId) {
        List<Folder> folder = folderService.getFolderByUserId(userId);
        logger.info("Get Folders By UserId:" + folder);
        return ResponseEntity.ok(folder);
    }

}
