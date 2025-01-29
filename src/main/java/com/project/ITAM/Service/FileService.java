package com.project.ITAM.Service;

import com.project.ITAM.Model.FileEntity;

import java.util.List;

public interface FileService {

    public FileEntity uploadFile(FileEntity file,Long folderId);

    public List<FileEntity> getFilesInFolder(Long folderId);
}
