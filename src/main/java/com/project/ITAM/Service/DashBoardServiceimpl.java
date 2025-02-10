package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DashBoardServiceimpl implements  DashBoardService{
    @Autowired
    private DashBoardRepo dashBoardRepo;

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private ObjectRepo objectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    LocalDateTime updateddate = LocalDateTime.now(ZoneId.systemDefault());
    String formattedDate = updateddate.format(DateTimeFormatter. ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Override
    public DashBoard uploadDashBoard(DashBoardRequest dashBoardRequest) {
        Folder folder = new Folder();
        if(dashBoardRequest.getFolderId()!=null) {
             folder = folderRepo.findById(dashBoardRequest.getFolderId()).orElseThrow(() -> new NotFoundException("Folder not found"));
        }
        ObjectEntity objectEntity = new ObjectEntity();
        if(dashBoardRequest.getObjectId()!=null) {
            objectEntity = objectRepo.findById(dashBoardRequest.getObjectId()).orElseThrow(() -> new NotFoundException("object not found"));
        }
        Set<Users> allowedUsers = new HashSet<>();
        Set<Groups> allowedGroups = new HashSet<>();
        if (dashBoardRequest.getFolderType() == FolderType.Restricted) {
            if (!CollectionUtils.isEmpty(dashBoardRequest.getUserIds())) {
                allowedUsers = userRepo.findAllById(dashBoardRequest.getUserIds()).stream().collect(Collectors.toSet());
            }
            if (!CollectionUtils.isEmpty(dashBoardRequest.getGroupIds())) {
                allowedGroups = groupRepo.findAllById(dashBoardRequest.getGroupIds()).stream().collect(Collectors.toSet());
            }
        }
        return dashBoardRepo.save(DashBoard.builder().dashBoardName(dashBoardRequest.getDashboardName())
                .createdBy("dafault").createdTime(formattedDate).users(allowedUsers).groups(allowedGroups)
                        .accessType(dashBoardRequest.getFolderType()).chartType(dashBoardRequest.getChartType())
                        .tableNames(dashBoardRequest.getTableName())
                        .columnNames(dashBoardRequest.getColumnNames()).description(dashBoardRequest.getDescription())
                .folder(folder).object(objectEntity).build());
    }

    @Override
    public List<DashBoard> getDashboardInFolder(Long folderId) {
        return dashBoardRepo.findByFolderId(folderId);
    }

    @Override
    public List<DashBoard> getAllDashboard() {
        return dashBoardRepo.findAll();
    }

    @Override
    public DashBoard updatedashBoard(Long id, DashBoardRequest dashBoardRequest) {
        DashBoard dashBoard = dashBoardRepo.findById(id).orElseThrow(()->new NotFoundException("dashBoard id not exist"));
        if(!StringUtils.isEmpty(dashBoardRequest.getDashboardName())) {
            dashBoard.setDashBoardName(dashBoardRequest.getDashboardName());
        }
        if(!StringUtils.isEmpty(dashBoardRequest.getChartType())) {
            dashBoard.setChartType(dashBoardRequest.getChartType());
        }
        if(!StringUtils.isEmpty(dashBoardRequest.getDescription())) {
            dashBoard.setDescription(dashBoardRequest.getDescription());
        }
        if(!StringUtils.isEmpty(dashBoardRequest.getFolderType().toString())) {
            dashBoard.setAccessType(dashBoardRequest.getFolderType());
        }
        if(!CollectionUtils.isEmpty(dashBoardRequest.getColumnNames())) {
            dashBoard.setColumnNames(dashBoardRequest.getColumnNames());
        }
        if(!CollectionUtils.isEmpty(dashBoardRequest.getTableName())) {
           dashBoard.setTableNames(dashBoardRequest.getTableName());
        }
        if(dashBoardRequest.getFolderId()!=null) {
            Optional<Folder> folder =folderRepo.findById(dashBoardRequest.getFolderId());
            folder.ifPresent(dashBoard::setFolder);
        }
        if(dashBoardRequest.getObjectId()!=null) {
            Optional<ObjectEntity> objectEntity =objectRepo.findById(dashBoardRequest.getObjectId());
            objectEntity.ifPresent(dashBoard::setObject);
        }
        if(dashBoard.getAccessType()== FolderType.Restricted && !CollectionUtils.isEmpty(dashBoardRequest.getUserIds())) {
            // Fetch users from the database
            Set<Users> users = new HashSet<>(userRepo.findAllById(dashBoardRequest.getUserIds()));
            // Update allowed users
            dashBoard.setUsers(users);
        }
        if(dashBoard.getAccessType()== FolderType.Restricted && !CollectionUtils.isEmpty(dashBoardRequest.getGroupIds())) {
            // Fetch groups from the database
            Set<Groups> groups = new HashSet<>(groupRepo.findAllById(dashBoardRequest.getGroupIds()));
            // Update allowed groups
            dashBoard.setGroups(groups);
        }

           dashBoard.setUpdatedBy("default");
        dashBoard.setUpdatedTime(formattedDate);
        return dashBoardRepo.save(dashBoard);
    }

    @Override
    public void deleteDashBoard(Long id) {
        if (!dashBoardRepo.existsById(id)) {
            throw new NotFoundException("dashboard with ID " + id + " not found");
        }
        DashBoard dashBoard = dashBoardRepo.findById(id).orElseThrow(()->new NotFoundException("dashBoard id not exist"));
        // Remove the folder from all groups before deleting
        for (Groups groups : groupRepo.findAll()) {
            groups.getAccessibleFolders().remove(dashBoard);
        }
        groupRepo.saveAll(groupRepo.findAll());

        // Remove the folder from all users before deleting
        for (Users users : userRepo.findAll()) {
            users.getAccessibleFolders().remove(dashBoard);
        }
        userRepo.saveAll(userRepo.findAll());

        dashBoardRepo.deleteById(id);
    }
}
