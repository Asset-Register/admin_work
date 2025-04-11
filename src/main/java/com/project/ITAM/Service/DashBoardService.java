package com.project.ITAM.Service;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.FileEntityRequest;

import java.util.List;

public interface DashBoardService {

    public DashBoard uploadDashBoard(DashBoardRequest dashBoard);

    public List<DashBoard> getDashboardInFolder(Long folderId);

    public List<DashBoard> getDashboardBasedOnObjects(Long objectId);

    public List<DashBoard> getAllDashboard();

    public DashBoard updatedashBoard(Long id, DashBoardRequest dashBoardRequest);

    public void deleteDashBoard(Long id);

    public List<DashBoard> getSelectedColumnValueDashBoard(Long folderId);

    public boolean checkdashBoardUniqueNameInSameObjectandFolder(Long folderId,Long ObjectId,String dashBoardName);
}
