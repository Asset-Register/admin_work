package com.project.ITAM.Service;

import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.FileEntityRequest;

import java.util.List;

public interface FileService {

    public FileEntity uploadFile(FileEntityRequest fileEntityRequest);

    public List<FileEntity> getFilesInFolder(Long folderId);
}
