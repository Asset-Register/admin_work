package com.project.ITAM.Service;

import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.FileEntityRequest;
import com.project.ITAM.Model.Folder;
import com.project.ITAM.Repository.FileRepo;
import com.project.ITAM.Repository.FolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private FolderRepo folderRepo;

    @Override
    public FileEntity uploadFile(FileEntityRequest fileEntityRequest) {
        Folder folder = new Folder();
        if(fileEntityRequest.getFolderId()!=null) {
             folder = folderRepo.findById(fileEntityRequest.getFolderId()).orElseThrow(() -> new RuntimeException("Folder not found"));
        }
        return fileRepo.save(FileEntity.builder().fileName(fileEntityRequest.getFileName()).filePath(fileEntityRequest.getFilePath()).fileType(fileEntityRequest.getFileType()).folder(folder).build());
    }

    @Override
    public List<FileEntity> getFilesInFolder(Long folderId) {
        return fileRepo.findByFolderId(folderId);
    }
}
