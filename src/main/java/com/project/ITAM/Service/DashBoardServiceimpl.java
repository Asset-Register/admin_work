package com.project.ITAM.Service;

import com.project.ITAM.Model.DashBoard;
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
    public DashBoard uploadFile(DashBoard dashBoard, Long folderId) {
        Folder folder = folderRepo.findById(folderId).orElseThrow(() -> new RuntimeException("Folder not found"));
DashBoard dashBoard1 = new DashBoard();
dashBoard1.setDashboardLink(dashBoard.getDashboardLink());
dashBoard1.setFolder(folder);

        return dashBoardRepo.save(dashBoard1);
    }

    @Override
    public List<DashBoard> getDashboardInFolder(Long folderId) {
        return dashBoardRepo.findByFolderId(folderId);
    }
}
