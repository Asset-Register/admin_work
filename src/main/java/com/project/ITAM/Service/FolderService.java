package com.project.ITAM.Service;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.FolderDTO;
import com.project.ITAM.Model.FolderRequest;

import java.util.List;

public interface FolderService {
    public Folder createFolder(FolderRequest folder, Long UserId);

    public Folder updateFolder(FolderRequest folder, Long UserId);

    public FolderDTO getFolderById(Long folderId,String sourceType);

    public List<FolderDTO> getAllFolders(String sourceType);

    public List<FolderDTO> getParentFolders(String sourceType);

    public List<FolderDTO> getFolderByUserId(Long folderId,String sourceType);

    public List<FolderDTO> getFolderByUserIdANDGroupID(Long folderId,String sourceType);

    public void deleteByFolderId(Long folderId,String sourceType);

}
