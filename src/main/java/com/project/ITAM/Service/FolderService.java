package com.project.ITAM.Service;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.FolderDTO;
import com.project.ITAM.Model.FolderRequest;

import java.util.List;

public interface FolderService {
    public Folder createFolder(FolderRequest folder, Long UserId);

    public Folder updateFolder(FolderRequest folder, Long UserId);

    public FolderDTO getFolderById(Long folderId);

    public List<FolderDTO> getAllFolders();

    public List<FolderDTO> getParentFolders();

    public List<FolderDTO> getFolderByUserId(Long folderId);

    public List<FolderDTO> getFolderByUserIdANDGroupID(Long folderId);

    public void deleteByFolderId(Long folderId);

}
