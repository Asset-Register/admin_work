package com.project.ITAM.Controller;

import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.Role;
import com.project.ITAM.Repository.FileRepo;
import com.project.ITAM.Repository.FolderRepo;
import com.project.ITAM.Service.FolderService;
import jakarta.validation.Valid;
import jakarta.ws.rs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/add")
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder,@RequestParam(required = false) Long parentId) {

        return ResponseEntity.ok(folderService.createFolder(folder,parentId));
    }

    @GetMapping("/folders")
    public ResponseEntity<List<Folder>> getAllFolders() {
        return ResponseEntity.ok(folderRepo.findAll());
    }

    @GetMapping("/folders/root")
    public ResponseEntity<List<Folder>> getRootFolders() {
        return ResponseEntity.ok(folderRepo.findByParentFolderIsNull());
    }

}
