package com.project.ITAM.Service;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.FolderRequest;

import java.util.List;

public interface FolderService {
    public Folder createFolder(FolderRequest folder, Long UserId);

    public Folder getFolderById(Long folderId);

    public List<Folder> getFolderByUserId(Long folderId);

}
