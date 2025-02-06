package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.FolderRepo;
import com.project.ITAM.Repository.GroupRepo;
import com.project.ITAM.Repository.ObjectRepo;
import com.project.ITAM.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FolderServiceimpl implements FolderService{

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private ObjectRepo objectRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    LocalDateTime updateddate = LocalDateTime.now(ZoneId.systemDefault());
    String formattedDate = updateddate.format(DateTimeFormatter. ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Override
    public Folder createFolder(FolderRequest folderRequest, Long userId) {
        Folder folder = new Folder();
        folder.setFolderName(folderRequest.getFolderName());
        folder.setFolderType(folderRequest.getFolderType());
        if (userId != null) {
            Users users = userRepo.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Parent folder not found"));
            folder.setUser(users);
        }

        if (folderRequest.getFolderType() == FolderType.Restricted) {
            if(!CollectionUtils.isEmpty(folderRequest.getUserIds())) {
                Set<Users>    allowedUsers = userRepo.findAllById(folderRequest.getUserIds()).stream().collect(Collectors.toSet());
                folder.setAllowedUsers(allowedUsers);
            }
            if(!CollectionUtils.isEmpty(folderRequest.getGroupIds())) {
                Set<Groups>  allowedGroups = groupRepo.findAllById(folderRequest.getGroupIds()).stream().collect(Collectors.toSet());
                folder.setAllowedGroups(allowedGroups);
            }
            if(!CollectionUtils.isEmpty(folderRequest.getObjectIds())) {
                Set<ObjectEntity> allowedObjects = objectRepo.findAllById(folderRequest.getObjectIds()).stream().collect(Collectors.toSet());
                folder.setAllowedObjects(allowedObjects);
            }
        }

        if (folderRequest.getParentFolderId() != null) {
            Folder parentFolder = folderRepo.findById(folderRequest.getParentFolderId())
                    .orElse(null);
            folder.setParentFolder(parentFolder);
        }
            folder.setCreatedBy("default");
        folder.setCreatedTime(formattedDate);
        return folderRepo.save(folder);
    }

    @Override
    public Folder updateFolder(FolderRequest folderRequest, Long id) {
        Folder folder = folderRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("folder not found"));
        if(folderRequest.getParentFolderId()!=null) {
            Folder parent_folder = folderRepo.findById(folderRequest.getParentFolderId())
                    .orElseThrow(() -> new NotFoundException("Entered parent Folder not present"));
            folder.setParentFolder(parent_folder);
        }

        if(!StringUtils.isEmpty(folderRequest.getFolderType())) {
            folder.setFolderType(folderRequest.getFolderType());
        }

        if(folder.getFolderType()== FolderType.Restricted && !CollectionUtils.isEmpty(folderRequest.getUserIds())) {
            // Fetch users from the database
            Set<Users> users = userRepo.findAllById(folderRequest.getUserIds()).stream().collect(Collectors.toSet());
            // Update allowed users
            folder.setAllowedUsers(users);
        }
        if(folder.getFolderType()== FolderType.Restricted && !CollectionUtils.isEmpty(folderRequest.getGroupIds())) {
            // Fetch groups from the database
            Set<Groups> groups = groupRepo.findAllById(folderRequest.getGroupIds()).stream().collect(Collectors.toSet());
            // Update allowed groups
            folder.setAllowedGroups(groups);
        }
        if(folder.getFolderType()== FolderType.Restricted && !CollectionUtils.isEmpty(folderRequest.getObjectIds())) {
            // Fetch objects from the database
            Set<ObjectEntity> objects = objectRepo.findAllById(folderRequest.getObjectIds()).stream().collect(Collectors.toSet());
            // Update allowed objects
            folder.setAllowedObjects(objects);
        }

        if(!StringUtils.isEmpty(folderRequest.getFolderName())) {
           folder.setFolderName(folderRequest.getFolderName());
        }
            folder.setUpdatedBy("default");
        folder.setUpdatedTime(formattedDate);
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

    @Override
    public List<Folder> getFolderByUserIdANDGroupID(Long userId) {
        return folderRepo.findAccessFolders(userId);
    }

    @Override
    public void deleteByFolderId(Long folderId) {
        if (!folderRepo.existsById(folderId)) {
            throw new NotFoundException("folder with ID " + folderId + " not found");
        }
        folderRepo.deleteById(folderId);
    }
}
