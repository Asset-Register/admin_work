package com.project.ITAM.Service;

import com.project.ITAM.Model.Folder;

public interface FolderService {
    public Folder createFolder(Folder folder,Long parentId);

}
