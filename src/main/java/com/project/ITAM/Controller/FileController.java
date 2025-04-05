package com.project.ITAM.Controller;

import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.FileEntityRequest;
import com.project.ITAM.Model.Groups;
import com.project.ITAM.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/data/files")
@CrossOrigin
@Validated
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@RequestBody FileEntityRequest fileEntityRequest) {
        return ResponseEntity.ok(fileService.uploadFile(fileEntityRequest));
    }

    @GetMapping("/{folderId}/get")
    public ResponseEntity<List<FileEntity>> getFilesInFolder(@PathVariable("folderId") Long folderId) {
        return ResponseEntity.ok(fileService.getFilesInFolder(folderId));
    }

    @PatchMapping("/{fileId}/update")
    public ResponseEntity<FileEntity> updateFileName(@PathVariable("fileId") Long fileId,@RequestBody FileEntityRequest fileEntityRequest){
        return ResponseEntity.ok(fileService.updateFile(fileId,fileEntityRequest));
    }

    @DeleteMapping("/{fileId}/delete")
    public String deleteByFileId(@PathVariable("fileId") Long fileId){
        fileService.deleteFile(fileId);
        return "file deleted";
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<FileEntity>> getAllGroups(){
        return  ResponseEntity.ok(fileService.getAllFiles());
    }
}
