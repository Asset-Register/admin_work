package com.project.ITAM.Controller;

import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@RequestBody FileEntity file,@RequestParam Long folderId) {
        return ResponseEntity.ok(fileService.uploadFile(file,folderId));
    }

    @GetMapping("/{folderId}/files")
    public ResponseEntity<List<FileEntity>> getFilesInFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(fileService.getFilesInFolder(folderId));
    }
}
