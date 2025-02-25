package com.project.ITAM.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.FileEntityRequest;
import com.project.ITAM.Model.SavedView;
import com.project.ITAM.Model.SavedViewRequest;

import java.util.List;

public interface SavedViewService {

    public SavedView saveView(SavedViewRequest savedViewRequest) throws JsonProcessingException;

    public List<SavedView> getViewssInFolder(Long folderId);

    public SavedViewRequest getViewssById(Long viewId) throws JsonProcessingException;

    public List<SavedView> getAllViews();

    public SavedView updateView(Long id,SavedViewRequest savedViewRequest) throws JsonProcessingException;

    public void deleteView(Long id);
}
