package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.FileEntityRequest;
import com.project.ITAM.Model.Folder;
import com.project.ITAM.Repository.FileRepo;
import com.project.ITAM.Repository.FolderRepo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private FolderRepo folderRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    LocalDateTime updateddate = LocalDateTime.now(ZoneId.systemDefault());
    String formattedDate = updateddate.format(DateTimeFormatter. ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Override
    public FileEntity uploadFile(FileEntityRequest fileEntityRequest) {
        Folder folder = new Folder();
        if(fileEntityRequest.getFolderId()!=null) {
             folder = folderRepo.findById(fileEntityRequest.getFolderId()).orElseThrow(() -> new NotFoundException("Folder not found"));
        }
        return fileRepo.save(FileEntity.builder().createdBy("default").createdTime(formattedDate)
                .fileName(fileEntityRequest.getFileName()).filePath(fileEntityRequest.getFilePath()).fileType(fileEntityRequest.getFileType()).folder(folder).build());
    }

    @Override
    public List<FileEntity> getFilesInFolder(Long folderId) {
        return fileRepo.findByFolderId(folderId);
    }

    @Override
    public List<FileEntity> getAllFiles() {
        return fileRepo.findAll();
    }

    @Override
    public FileEntity updateFile(Long id, FileEntityRequest fileEntityRequest) {
        FileEntity file = fileRepo.findById(id).orElseThrow(()->new NotFoundException("file id not exist"));
        if(!StringUtils.isEmpty(fileEntityRequest.getFileName())) {
           file.setFileName(fileEntityRequest.getFileName());
        }
        if(!StringUtils.isEmpty(fileEntityRequest.getFilePath())) {
            file.setFilePath(fileEntityRequest.getFilePath());
        }
        if(fileEntityRequest.getFolderId()!=null) {
           Optional<Folder> folder =folderRepo.findById(fileEntityRequest.getFolderId());
            if(folder.isPresent()) {
                file.setFolder(folder.get());
            }
        }
           file.setUpdatedBy("default");
        file.setUpdatedTime(formattedDate);
        return fileRepo.save(file);
    }

    @Override
    public void deleteFile(Long id) {
        if (!fileRepo.existsById(id)) {
            throw new NotFoundException("file with ID " + id + " not found");
        }
        fileRepo.deleteById(id);
    }
}
