package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.*;
import com.project.ITAM.client.ITAMClient;
import com.project.ITAM.helper.DateTimeUtil;
import com.project.ITAM.helper.ExtractJsonUtil;
import feign.FeignException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashBoardServiceimpl implements  DashBoardService {
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

    @Autowired
    private ITAMClient itamClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * update dashBoard
     *
     * @param dashBoardRequest
     * @return
     */
    @Override
    public DashBoard uploadDashBoard(DashBoardRequest dashBoardRequest) {
        Folder folder = new Folder();
        if (dashBoardRequest.getFolderId() != null) {
            folder = folderRepo.findById(dashBoardRequest.getFolderId()).orElseThrow(() -> new NotFoundException("Folder not found"));
        }
        ObjectEntity objectEntity = new ObjectEntity();
        if (dashBoardRequest.getObjectId() != null) {
            objectEntity = objectRepo.findById(dashBoardRequest.getObjectId()).orElseThrow(() -> new NotFoundException("object not found"));
        }
        Set<Users> allowedUsers = new HashSet<>();
        Set<Groups> allowedGroups = new HashSet<>();
        if (dashBoardRequest.getFolderType() == AccessType.Restricted) {
            if (!CollectionUtils.isEmpty(dashBoardRequest.getUserIds())) {
                allowedUsers = userRepo.findAllById(dashBoardRequest.getUserIds()).stream().collect(Collectors.toSet());
            }
            if (!CollectionUtils.isEmpty(dashBoardRequest.getGroupIds())) {
                allowedGroups = groupRepo.findAllById(dashBoardRequest.getGroupIds()).stream().collect(Collectors.toSet());
            }
        }
        return dashBoardRepo.save(DashBoard.builder().dashBoardName(dashBoardRequest.getDashboardName())
                .createdBy(ExtractJsonUtil.getUserdetails()).createdTime(DateTimeUtil.currentDateTime()).users(allowedUsers).groups(allowedGroups)
                .accessType(dashBoardRequest.getFolderType()).chartType(dashBoardRequest.getChartType())
                .tableNames(dashBoardRequest.getTableName())
                .columnNames(dashBoardRequest.getColumnNames()).description(dashBoardRequest.getDescription())
                .folder(folder).object(objectEntity).build());
    }

    /**
     * get dashboard in folder
     *
     * @param folderId
     * @return
     */
    @Override
    public List<DashBoard> getDashboardInFolder(Long folderId) {
        return dashBoardRepo.findByFolderId(folderId);
    }

    @Override
    public List<DashBoard> getDashboardBasedOnObjects(Long objectId) {
        return dashBoardRepo.findByObjectId(objectId);
    }

    /**
     * get ALl dashBoard
     *
     * @return
     */
    @Override
    public List<DashBoard> getAllDashboard() {
        return dashBoardRepo.findAll();
    }

    /**
     * update DashBoard
     *
     * @param id
     * @param dashBoardRequest
     * @return
     */
    @Override
    public DashBoard updatedashBoard(Long id, DashBoardRequest dashBoardRequest) {
        DashBoard dashBoard = dashBoardRepo.findById(id).orElseThrow(() -> new NotFoundException("dashBoard id not exist"));
        if (!StringUtils.isEmpty(dashBoardRequest.getDashboardName())) {
            dashBoard.setDashBoardName(dashBoardRequest.getDashboardName());
        }
        if (!StringUtils.isEmpty(dashBoardRequest.getChartType())) {
            dashBoard.setChartType(dashBoardRequest.getChartType());
        }
        if (!StringUtils.isEmpty(dashBoardRequest.getDescription())) {
            dashBoard.setDescription(dashBoardRequest.getDescription());
        }
        if (!ObjectUtils.isEmpty(dashBoardRequest.getFolderType())) {
            dashBoard.setAccessType(dashBoardRequest.getFolderType());
        }
        if (!CollectionUtils.isEmpty(dashBoardRequest.getColumnNames())) {
            dashBoard.setColumnNames(dashBoardRequest.getColumnNames());
        }
        if (!CollectionUtils.isEmpty(dashBoardRequest.getTableName())) {
            dashBoard.setTableNames(dashBoardRequest.getTableName());
        }
        if (dashBoardRequest.getFolderId() != null) {
            Optional<Folder> folder = folderRepo.findById(dashBoardRequest.getFolderId());
            folder.ifPresent(dashBoard::setFolder);
        }
        if (dashBoardRequest.getObjectId() != null) {
            Optional<ObjectEntity> objectEntity = objectRepo.findById(dashBoardRequest.getObjectId());
            objectEntity.ifPresent(dashBoard::setObject);
        }
        if (dashBoard.getAccessType() == AccessType.Restricted && !CollectionUtils.isEmpty(dashBoardRequest.getUserIds())) {
            // Fetch users from the database
            Set<Users> users = new HashSet<>(userRepo.findAllById(dashBoardRequest.getUserIds()));
            // Update allowed users
            dashBoard.setUsers(users);
        }
        if (dashBoard.getAccessType() == AccessType.Restricted && !CollectionUtils.isEmpty(dashBoardRequest.getGroupIds())) {
            // Fetch groups from the database
            Set<Groups> groups = new HashSet<>(groupRepo.findAllById(dashBoardRequest.getGroupIds()));
            // Update allowed groups
            dashBoard.setGroups(groups);
        }

        dashBoard.setUpdatedBy(ExtractJsonUtil.getUserdetails());
        dashBoard.setUpdatedTime(DateTimeUtil.currentDateTime());
        return dashBoardRepo.save(dashBoard);
    }

    /**
     * Delete DashBoard
     *
     * @param id
     */
    @Override
    public void deleteDashBoard(Long id) {
        if (!dashBoardRepo.existsById(id)) {
            throw new NotFoundException("dashboard with ID " + id + " not found");
        }
        dashBoardRepo.deleteById(id);
    }

    /**
     * in particular folder get the dashboards
     * Each dashboard get the columnNames from Table
     * From itamClient api we get the value of columnNames selected  for particular table
     * group the unique records key (columnname and value ) and value -> counting of unique values
     *
     * @param folderId
     * @return
     */
    @Override
    public List<DashBoard> getSelectedColumnValueDashBoard(Long folderId) {
       if(folderRepo.existsById(folderId)) {
           List<DashBoard> dashBoards = dashBoardRepo.findByFolderId(folderId);
           if (!CollectionUtils.isEmpty(dashBoards)) {
               for (DashBoard dashBoard : dashBoards) {
                   for (String tableName : dashBoard.getTableNames()) {
                       List<String> columnNames = dashBoard.getColumnNames().entrySet().stream()
                               .filter(entry -> entry.getKey().equalsIgnoreCase(tableName))
                               .flatMap(entry -> entry.getValue().stream())
                               .collect(Collectors.toList());
                       // .collect(Collectors.joining(","));
                       //  String encodedColumns = URLDecoder.decode(columnNames, StandardCharsets.UTF_8);
                       //   URI uri = URI.create(URLDecoder.decode(finalUrl, StandardCharsets.UTF_8));
                       List<Map<String, Object>> columnNamesWithValues = new ArrayList<>();
                       try {
                           columnNamesWithValues = itamClient
                                   .getColumnValues(tableName, columnNames);
                       } catch (FeignException.NotFound ex) {
                           logger.warn("tablename {} not found.", tableName);
                     //      throw new NotFoundException("viewid not found");
                       } catch (FeignException ex) {
                           logger.error("Feign client error: {}", ex.getMessage());
                     //      throw new NotFoundException("Error while fetching table " + ex.getMessage());
                       }
                       // group the unique records key (columnname and value ) and value -> counting of unique values
                       Map<Map<String, Object>, Long> groupedRecords = columnNamesWithValues.stream()
                               .collect(Collectors.groupingBy(
                                       map -> new HashMap<>(map),  // Ensures a proper key instance
                                       Collectors.counting()
                               ));
                       dashBoard.setColumnNamesWithValuesANDCounting(groupedRecords);
                       // logger.info("dashBoard with uniqueColumns"+dashBoard);
                   }
               }
               logger.info("dashBoard with uniqueColumns" + dashBoards);
               return dashBoards;
           } else {
               new NotFoundException("dashboard not exist in folderId"+folderId);
           }
       }else{
           new NotFoundException("folder id not exist");
       }
        return Collections.emptyList();
    }

    @Override
    public boolean checkdashBoardUniqueNameInSameObjectandFolder(Long folderId, Long objectId, String dashBoardName) {
        return dashBoardRepo.existsByDashBoardNameAndObject_objectIdAndFolder_id(dashBoardName,objectId,folderId);
    }
}
