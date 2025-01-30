package com.project.ITAM.Service;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Model.Folder;
import com.project.ITAM.Repository.DashBoardRepo;
import com.project.ITAM.Repository.FolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardServiceimpl implements  DashBoardService{
    @Autowired
    private DashBoardRepo dashBoardRepo;

    @Autowired
    private FolderRepo folderRepo;

    @Override
    public DashBoard uploadFile(DashBoardRequest dashBoardRequest) {
        Folder folder = new Folder();
        if(dashBoardRequest.getFolderId()!=null) {
             folder = folderRepo.findById(dashBoardRequest.getFolderId()).orElseThrow(() -> new RuntimeException("Folder not found"));
        }
        return dashBoardRepo.save(DashBoard.builder().dashboardLink(dashBoardRequest.getDashboardLink()).folder(folder).build());
    }

    @Override
    public List<DashBoard> getDashboardInFolder(Long folderId) {
        return dashBoardRepo.findByFolderId(folderId);
    }
}
