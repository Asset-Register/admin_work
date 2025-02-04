package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.DashBoardRepo;
import com.project.ITAM.Repository.FolderRepo;
import com.project.ITAM.Repository.ObjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class DashBoardServiceimpl implements  DashBoardService{
    @Autowired
    private DashBoardRepo dashBoardRepo;

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private ObjectRepo objectRepo;

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

        return dashBoardRepo.save(DashBoard.builder().dashBoardName(dashBoardRequest.getDashboardName())
                        .accessType(dashBoardRequest.getAccessType()).dashboardType(dashBoardRequest.getDashBoardType())
                        .sourceName(dashBoardRequest.getSourceName()).tableName(dashBoardRequest.getTableName()).description(dashBoardRequest.getDescription())
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
        if(!StringUtils.isEmpty(dashBoardRequest.getDashBoardType())) {
            dashBoard.setDashboardType(dashBoardRequest.getDashBoardType());
        }
        if(!StringUtils.isEmpty(dashBoardRequest.getDescription())) {
            dashBoard.setDescription(dashBoardRequest.getDescription());
        }
        if(!StringUtils.isEmpty(dashBoardRequest.getAccessType())) {
            dashBoard.setAccessType(dashBoardRequest.getAccessType());
        }
        if(!StringUtils.isEmpty(dashBoardRequest.getSourceName())) {
            dashBoard.setSourceName(dashBoardRequest.getSourceName());
        }
        if(!StringUtils.isEmpty(dashBoardRequest.getTableName())) {
            dashBoard.setTableName(dashBoardRequest.getTableName());
        }
        if(dashBoardRequest.getFolderId()!=null) {
            Optional<Folder> folder =folderRepo.findById(dashBoardRequest.getFolderId());
            folder.ifPresent(dashBoard::setFolder);
        }
        if(dashBoardRequest.getObjectId()!=null) {
            Optional<ObjectEntity> objectEntity =objectRepo.findById(dashBoardRequest.getObjectId());
            objectEntity.ifPresent(dashBoard::setObject);
        }

        return dashBoardRepo.save(dashBoard);
    }

    @Override
    public void deleteDashBoard(Long id) {
        if (!dashBoardRepo.existsById(id)) {
            throw new NotFoundException("dashboard with ID " + id + " not found");
        }
        dashBoardRepo.deleteById(id);
    }
}
