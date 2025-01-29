package com.project.ITAM.Service;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Repository.FolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolderServiceimpl implements FolderService{

    @Autowired
    private FolderRepo folderRepo;

    @Override
    public Folder createFolder(Folder folder, Long parentId) {
        Folder folder1 = new Folder();
        folder1.setFolderName(folder.getFolderName());
        folder1.setParentType(folder.getParentType());

        if (parentId != null) {
            Folder parentFolder = folderRepo.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
            folder1.setParentFolder(parentFolder);
        }

        return folderRepo.save(folder1);
    }
}
