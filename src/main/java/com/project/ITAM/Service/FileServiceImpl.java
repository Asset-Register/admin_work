package com.project.ITAM.Service;

import com.project.ITAM.Model.FileEntity;
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
    public FileEntity uploadFile(FileEntity file,Long folderId) {
        Folder folder = folderRepo.findById(folderId).orElseThrow(() -> new RuntimeException("Folder not found"));

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getFileName());
        fileEntity.setFileType(file.getFileType());
        fileEntity.setFilePath(file.getFilePath());
        fileEntity.setFolder(folder);

        return fileRepo.save(fileEntity);
    }

    @Override
    public List<FileEntity> getFilesInFolder(Long folderId) {
        return fileRepo.findByFolderId(folderId);
    }
}
