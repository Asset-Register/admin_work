package com.project.ITAM.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.*;
import com.project.ITAM.Repository.*;
import com.project.ITAM.client.ITAMClient;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SavedViewServiceImpl implements SavedViewService{

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private SaveViewRepo saveViewRepo;

    @Autowired
    private ObjectRepo objectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
            private ITAMClient itamClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    LocalDateTime updateddate = LocalDateTime.now(ZoneId.systemDefault());
    String formattedDate = updateddate.format(DateTimeFormatter. ofPattern("yyyy-MM-dd HH:mm:ss"));
     ObjectMapper mapper = new ObjectMapper();

    @Override
    public SavedView saveView(SavedViewRequest savedViewRequest) throws JsonProcessingException {
        Folder folder = new Folder();
        if(savedViewRequest.getFolderId()!=null) {
            folder = folderRepo.findById(savedViewRequest.getFolderId()).orElseThrow(() -> new NotFoundException("Folder not found"));
        }
        String jsonString = mapper.writeValueAsString(savedViewRequest.getFilters());

        ObjectEntity objectEntity = new ObjectEntity();
        JobSchedule jobSchedule = itamClient.viewJobs(savedViewRequest.getJobName()).orElseThrow(()-> new NotFoundException("job not found"));
            objectEntity = objectRepo.findById(jobSchedule.getObject()).orElseThrow(() -> new NotFoundException("object not found"));

        Set<Users> allowedUsers = new HashSet<>();
        Set<Groups> allowedGroups = new HashSet<>();
        if (savedViewRequest.getAccessType() == AccessType.Restricted) {
            if (!CollectionUtils.isEmpty(savedViewRequest.getUserIds())) {
                allowedUsers = userRepo.findAllById(savedViewRequest.getUserIds()).stream().collect(Collectors.toSet());
            }
            if (!CollectionUtils.isEmpty(savedViewRequest.getGroupIds())) {
                allowedGroups = groupRepo.findAllById(savedViewRequest.getGroupIds()).stream().collect(Collectors.toSet());
            }
        }

        return saveViewRepo.save(SavedView.builder().viewName(savedViewRequest.getViewName()).createdBy("default")
                        .groups(allowedGroups).users(allowedUsers).object(objectEntity).jobName(savedViewRequest.getJobName())
                        .dataSource(savedViewRequest.getDataSource()).accessType(savedViewRequest.getAccessType())
                .createdTime(formattedDate).filters(jsonString).folder(folder).build());
    }

    @Override
    public List<SavedView> getViewssInFolder(Long folderId) {
        return  saveViewRepo.findByFolderId(folderId);
    }

    @Override
    public SavedViewRequest getViewssById(Long viewId) throws JsonProcessingException {
        SavedView savedView = saveViewRepo.findById(viewId).orElseThrow(()->new NotFoundException("saved View id not exist"));
        FilterRequest filterRequest= new ObjectMapper().readValue(savedView.getFilters(),FilterRequest.class);
        return SavedViewRequest.builder().filters(filterRequest).viewName(savedView.getViewName())
                .dataSource(savedView.getDataSource()).accessType(savedView.getAccessType())
                .folderId(savedView.getId()).build();
    }

    @Override
    public List<SavedView> getAllViews() {
        return saveViewRepo.findAll();
    }

    @Override
    public SavedView updateView(Long id, SavedViewRequest savedViewRequest) throws JsonProcessingException {
        SavedView view = saveViewRepo.findById(id).orElseThrow(()->new NotFoundException("saved View id not exist"));
        if(!StringUtils.isEmpty(savedViewRequest.getViewName())) {
            view.setViewName(savedViewRequest.getViewName());
        }

        if(!ObjectUtils.isEmpty(savedViewRequest.getFilters())) {
            if(!StringUtils.isEmpty(savedViewRequest.getFilters().getSearchText())){
                savedViewRequest.getFilters().setSearchText(savedViewRequest.getFilters().getSearchText());
            }
            if(!StringUtils.isEmpty(savedViewRequest.getFilters().getSelectedKeys())){
                savedViewRequest.getFilters().setSelectedKeys(savedViewRequest.getFilters().getSelectedKeys());
            }
            if(!ObjectUtils.isEmpty(savedViewRequest.getFilters().getFilterExpression())) {
                if(!StringUtils.isEmpty(savedViewRequest.getFilters().getFilterExpression().getLogic())){
                    savedViewRequest.getFilters().getFilterExpression().setLogic(savedViewRequest.getFilters().getFilterExpression().getLogic());
                }
                if(!ObjectUtils.isEmpty(savedViewRequest.getFilters().getFilterExpression().getConditions())){
                    // Modify the conditions list (e.g., replace the first condition for demonstration)
                    List<FilterRequest.Condition> conditions = savedViewRequest.getFilters().getFilterExpression().getConditions();
                    if (!conditions.isEmpty()) {
                       savedViewRequest.getFilters().getFilterExpression().setConditions(conditions);
                    }
                }
            }
            String jsonString = mapper.writeValueAsString(savedViewRequest.getFilters());
            view.setFilters(jsonString);
        }
        if(savedViewRequest.getFolderId()!=null) {
            Optional<Folder> folder =folderRepo.findById(savedViewRequest.getFolderId());
            if(folder.isPresent()) {
                view.setFolder(folder.get());
            }
        }

        if(view.getAccessType()== AccessType.Restricted && !CollectionUtils.isEmpty(savedViewRequest.getUserIds())) {
            // Fetch users from the database
            Set<Users> users = new HashSet<>(userRepo.findAllById(savedViewRequest.getUserIds()));
            // Update allowed users
            view.setUsers(users);
        }
        if(view.getAccessType()== AccessType.Restricted && !CollectionUtils.isEmpty(savedViewRequest.getGroupIds())) {
            // Fetch groups from the database
            Set<Groups> groups = new HashSet<>(groupRepo.findAllById(savedViewRequest.getGroupIds()));
            // Update allowed groups
            view.setGroups(groups);
        }

        view.setUpdatedBy("default");
        view.setUpdatedTime(formattedDate);
        return saveViewRepo.save(view);
    }

    @Override
    public void deleteView(Long id) {
        if (!saveViewRepo.existsById(id)) {
            throw new NotFoundException("saved View with ID " + id + " not found");
        }
        saveViewRepo.deleteById(id);
    }
}
