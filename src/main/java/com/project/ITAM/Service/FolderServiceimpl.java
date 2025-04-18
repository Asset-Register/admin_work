package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.*;
import com.project.ITAM.helper.DateTimeUtil;
import com.project.ITAM.helper.ExtractJsonUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
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

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private DashBoardRepo dashBoardRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Folder createFolder(FolderRequest folderRequest, Long userId) {
        Folder folder = new Folder();
      boolean check=  folderRepo.existsByFolderNameAndSourceType(folderRequest.getFolderName(),folderRequest.getSourceType().toString());
      if(!check) {
          folder.setFolderName(folderRequest.getFolderName());
      }else{
          throw new NotFoundException("FolderName already exist for sourceType "+folderRequest.getSourceType().toString());
      }
        folder.setAccessType(folderRequest.getAccessType());
        if (userId != null) {
            Users users = userRepo.findById(userId)
                    .orElseThrow(() -> new NotFoundException("userId not found"));
            folder.setUser(users);
        }

        if (folderRequest.getAccessType() == AccessType.Restricted) {
            if(!CollectionUtils.isEmpty(folderRequest.getUserIds())) {
                Set<Users>    allowedUsers = new HashSet<>(userRepo.findAllById(folderRequest.getUserIds()));
                folder.setAllowedUsers(allowedUsers);
            }
            if(!CollectionUtils.isEmpty(folderRequest.getGroupIds())) {
                Set<Groups>  allowedGroups = new HashSet<>(groupRepo.findAllById(folderRequest.getGroupIds()));
                folder.setAllowedGroups(allowedGroups);
            }
            if(!CollectionUtils.isEmpty(folderRequest.getObjectIds())) {
                Set<ObjectEntity> allowedObjects = new HashSet<>(objectRepo.findAllById(folderRequest.getObjectIds()));
                folder.setAllowedObjects(allowedObjects);
            }
        }

        if (folderRequest.getParentFolderId() != null) {
            Folder parentFolder = folderRepo.findById(folderRequest.getParentFolderId())
                    .orElse(null);
            folder.setParentFolder(parentFolder);
        }
            folder.setCreatedBy(ExtractJsonUtil.getUserdetails());
        folder.setCreatedTime(DateTimeUtil.currentDateTime());
        folder.setSourceType(folderRequest.getSourceType().toString());
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

        if (folderRequest.getAccessType() != null && !folderRequest.getAccessType().toString().isEmpty()) {
            folder.setAccessType(folderRequest.getAccessType());
        }

        if(folder.getAccessType()== AccessType.Restricted && !CollectionUtils.isEmpty(folderRequest.getUserIds())) {
            // Fetch users from the database
            Set<Users> users = userRepo.findAllById(folderRequest.getUserIds()).stream().collect(Collectors.toSet());
            // Update allowed users
            folder.setAllowedUsers(users);
        }
        if(folder.getAccessType()== AccessType.Restricted && !CollectionUtils.isEmpty(folderRequest.getGroupIds())) {
            // Fetch groups from the database
            Set<Groups> groups = new HashSet<>(groupRepo.findAllById(folderRequest.getGroupIds()));
            // Update allowed groups
            folder.setAllowedGroups(groups);
        }
        if(folder.getAccessType()== AccessType.Restricted && !CollectionUtils.isEmpty(folderRequest.getObjectIds())) {
            // Fetch objects from the database
            Set<ObjectEntity> objects = new HashSet<>(objectRepo.findAllById(folderRequest.getObjectIds()));
            // Update allowed objects
            folder.setAllowedObjects(objects);
        }

        if(!StringUtils.isEmpty(folderRequest.getFolderName())) {
           folder.setFolderName(folderRequest.getFolderName());
        }
            folder.setUpdatedBy(ExtractJsonUtil.getUserdetails());
        folder.setUpdatedTime(DateTimeUtil.currentDateTime());
        return folderRepo.save(folder);
    }

    @Transactional
    @Override
    public FolderDTO getFolderById(Long folderId,String sourceType) {
        Folder folder = folderRepo.findByFolderidAndSourcetype(folderId,sourceType)
                .orElseThrow(()-> new NotFoundException("folder Id not found given"+sourceType));
        return convertToDTO(folder);
    }

    @Transactional
    @Override
    public List<FolderDTO> getAllFolders(String sourceType) {
        List<Folder> folders = folderRepo.findAll();
        List<Folder> folders1=  folders.stream().filter(folder-> folder.getSourceType().equalsIgnoreCase(sourceType)).toList();
        return folders1.stream()
                .map(this::convertToDTO) // Convert each Folder to FolderDTO
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<FolderDTO> getParentFolders(String sourceType) {
        List<Folder> folders = folderRepo.findByParentFolderIsNull();
        List<Folder> folders1=  folders.stream().filter(folder-> folder.getSourceType().equalsIgnoreCase(sourceType)).toList();
        return folders1.stream()
                .map(this::convertToDTO) // Convert each Folder to FolderDTO
                .collect(Collectors.toList());
    }

    private FolderDTO convertToDTO(Folder folder) {
        List<FolderDTO> childDTOs = folder.getChildFolders().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new FolderDTO(folder.getId(), folder.getFolderName(), childDTOs,folder.getUser().getUserId(),folder.getAccessType());
    }

    @Transactional
    @Override
    public List<FolderDTO> getFolderByUserId(Long userId,String sourceType) {
        List<Folder> folders =  folderRepo.findAccessibleFolders(userId);
        List<Folder> folders1=  folders.stream().filter(folder-> folder.getSourceType().equalsIgnoreCase(sourceType)).toList();
        return folders1.stream()
                .map(this::convertToDTO) // Convert each Folder to FolderDTO
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<FolderDTO> getFolderByUserIdANDGroupID(Long userId,String sourceType) {
         List<Folder> folders = folderRepo.findAccessFolders(userId);
        List<Folder> folders1=  folders.stream().filter(folder-> folder.getSourceType().equalsIgnoreCase(sourceType)).toList();
        return folders1.stream()
                .map(this::convertToDTO) // Convert each Folder to FolderDTO
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteByFolderId(Long folderId,String sourceType) {
        if (!folderRepo.existsById(folderId)) {
            throw new NotFoundException("folder with ID " + folderId + " not found");
        }
        Folder folder = folderRepo.findByFolderidAndSourcetype(folderId,sourceType)
                .orElseThrow(()-> new NotFoundException("folder Id not found given"+sourceType));
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
