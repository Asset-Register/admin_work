package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.Folder;
import com.project.ITAM.Repository.DashBoardRepo;
import com.project.ITAM.Repository.FolderRepo;
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

    @Override
    public DashBoard uploadDashBoard(DashBoardRequest dashBoardRequest) {
        Folder folder = new Folder();
        if(dashBoardRequest.getFolderId()!=null) {
             folder = folderRepo.findById(dashBoardRequest.getFolderId()).orElseThrow(() -> new NotFoundException("Folder not found"));
        }
        return dashBoardRepo.save(DashBoard.builder().dashboardLink(dashBoardRequest.getDashboardLink()).folder(folder).build());
    }

    @Override
    public List<DashBoard> getDashboardInFolder(Long folderId) {
        return dashBoardRepo.findByFolderId(folderId);
    }

    @Override
    public DashBoard updatedashBoard(Long id, DashBoardRequest dashBoardRequest) {
        DashBoard dashBoard = dashBoardRepo.findById(id).orElseThrow(()->new NotFoundException("dashBoard id not exist"));
        if(!StringUtils.isEmpty(dashBoardRequest.getDashboardLink())) {
            dashBoard.setDashboardLink(dashBoardRequest.getDashboardLink());
        }
        if(dashBoardRequest.getFolderId()!=null) {
            Optional<Folder> folder =folderRepo.findById(dashBoardRequest.getFolderId());
            if(folder.isPresent()) {
                dashBoard.setFolder(folder.get());
            }
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
