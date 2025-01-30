package com.project.ITAM.Service;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.FileEntity;

import java.util.List;

public interface DashBoardService {

    public DashBoard uploadFile(DashBoard dashBoard, Long folderId);

    public List<DashBoard> getDashboardInFolder(Long folderId);
}
