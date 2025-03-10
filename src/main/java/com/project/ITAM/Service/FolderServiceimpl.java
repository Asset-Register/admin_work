package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private DashBoardRepo dashBoardRepo;

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
                    .orElseThrow(() -> new NotFoundException("userId not found"));
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

        if(!StringUtils.isEmpty(folderRequest.getFolderType().toString())) {
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

    @Transactional
    @Override
    public FolderDTO getFolderById(Long folderId) {
        Folder folder = folderRepo.findById(folderId)
                .orElseThrow(() ->  new NotFoundException( + folderId + " not found"));
        return convertToDTO(folder);
    }

    @Transactional
    @Override
    public List<FolderDTO> getAllFolders() {
        List<Folder> folders = folderRepo.findAll();
        return folders.stream()
                .map(this::convertToDTO) // Convert each Folder to FolderDTO
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<FolderDTO> getParentFolders() {
        List<Folder> folders = folderRepo.findByParentFolderIsNull();
        return folders.stream()
                .map(this::convertToDTO) // Convert each Folder to FolderDTO
                .collect(Collectors.toList());
    }

    private FolderDTO convertToDTO(Folder folder) {
        List<FolderDTO> childDTOs = folder.getChildFolders().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new FolderDTO(folder.getId(), folder.getFolderName(), childDTOs,folder.getUser().getUserId(),folder.getFolderType());
    }

    @Transactional
    @Override
    public List<FolderDTO> getFolderByUserId(Long userId) {
        List<Folder> folders =  folderRepo.findAccessibleFolders(userId);
        return folders.stream()
                .map(this::convertToDTO) // Convert each Folder to FolderDTO
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<FolderDTO> getFolderByUserIdANDGroupID(Long userId) {
        List<Folder> folders = folderRepo.findAccessFolders(userId);
        return folders.stream()
                .map(this::convertToDTO) // Convert each Folder to FolderDTO
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteByFolderId(Long folderId) {
        if (!folderRepo.existsById(folderId)) {
            throw new NotFoundException("folder with ID " + folderId + " not found");
        }
        Folder folder = folderRepo.findById(folderId).orElseThrow(()-> new NotFoundException("folder Id not found"));
           fileRepo.deleteByFolder(folder);
            dashBoardRepo.deleteByFolder(folder);

        // Remove the folder from all groups before deleting
        for (Groups groups : folder.getAllowedGroups()) {
            groups.getAccessibleFolders().remove(folder);
        }
        groupRepo.saveAll(groupRepo.findAll());

        // Remove the folder from all users before deleting
        for (Users users : folder.getAllowedUsers()) {
            users.getAccessibleFolders().remove(folder);
        }
        userRepo.saveAll(userRepo.findAll());

        // Remove user associations
        folder.getAllowedUsers().clear();
        folder.getAllowedGroups().clear();
        folder.getAllowedObjects().clear();
        folderRepo.save(folder); // Save to persist the change

        // Remove the folder from all objects before deleting
        for (ObjectEntity object : folder.getAllowedObjects()) {
            object.getAccessibleFolders().remove(folder);
        }
        objectRepo.saveAll(objectRepo.findAll());

        folderRepo.delete(folder);
    }
}
