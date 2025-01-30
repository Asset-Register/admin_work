package com.project.ITAM.Service;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.FolderRequest;
import com.project.ITAM.Model.FolderType;
import com.project.ITAM.Model.Users;
import com.project.ITAM.Repository.FolderRepo;
import com.project.ITAM.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FolderServiceimpl implements FolderService{

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Folder createFolder(FolderRequest folderRequest, Long userId) {
        Folder folder = new Folder();
        folder.setFolderName(folderRequest.getFolderName());
        folder.setFolderType(folderRequest.getFolderType());
        if (userId != null) {
            Users users = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
            folder.setUser(users);
        }

        if (folderRequest.getFolderType() == FolderType.RESTRICTED) {
            Set<Users> allowedUsers = userRepo.findAllById(folderRequest.getUserIds()).stream().collect(Collectors.toSet());
            folder.setAllowedUsers(allowedUsers);
        }

        if (folderRequest.getParentFolderId() != null) {
            Folder parentFolder = folderRepo.findById(folderRequest.getParentFolderId())
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
            folder.setParentFolder(parentFolder);
        }

        return folderRepo.save(folder);
    }

    @Override
    public Folder getFolderById(Long folderId) {
        return folderRepo.getById(folderId);
    }

    @Override
    public List<Folder> getFolderByUserId(Long userId) {
        return folderRepo.findAccessibleFolders(userId);
    }
}
