package com.project.ITAM.Controller;

import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.FileEntityRequest;
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
    public ResponseEntity<FileEntity> uploadFile(@RequestBody FileEntityRequest fileEntityRequest) {
        return ResponseEntity.ok(fileService.uploadFile(fileEntityRequest));
    }

    @GetMapping("/{folderId}/get")
    public ResponseEntity<List<FileEntity>> getFilesInFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(fileService.getFilesInFolder(folderId));
    }

    @PatchMapping("/{fileId}/update")
    public ResponseEntity<FileEntity> updateFileName(@PathVariable Long fileId,@RequestBody FileEntityRequest fileEntityRequest){
        return ResponseEntity.ok(fileService.updateFile(fileId,fileEntityRequest));
    }

    @DeleteMapping("/{fileId}/delete")
    public String deleteByFileId(@PathVariable Long fileId){
        fileService.deleteFile(fileId);
        return "file deleted";
    }
}
