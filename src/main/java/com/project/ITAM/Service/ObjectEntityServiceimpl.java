package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.ObjectEntity;
import com.project.ITAM.Model.ObjectEntityRequest;
import com.project.ITAM.Repository.ObjectRepo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ObjectEntityServiceimpl implements ObjectEntityService{

    @Autowired
    private ObjectRepo objectRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    LocalDateTime updateddate = LocalDateTime.now(ZoneId.systemDefault());
    String formattedDate = updateddate.format(DateTimeFormatter. ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Override
    public ObjectEntity createObjectEntity(ObjectEntityRequest objectEntityRequest) {
        return objectRepo.save(ObjectEntity.builder().objectName(objectEntityRequest.getObjectName()).email(objectEntityRequest.getEmail())
                .createdBy("default").createdTime(formattedDate).build());
    }

    @Override
    public List<ObjectEntity> getAllObjects() {
        return objectRepo.findAll();
    }

    @Override
    public ObjectEntity getObjectById(Long objectId) {
        ObjectEntity objectEntity = objectRepo.findById(objectId).orElseThrow(() -> new NotFoundException("Object Id not found"));
        return objectEntity;
    }

    @Override
    public ObjectEntity updateObjects(Long objectId, ObjectEntityRequest objectEntityRequest) {
        ObjectEntity objectEntity = objectRepo.findById(objectId).orElseThrow(() -> new NotFoundException("Object Id not found"));
        if(!StringUtils.isEmpty(objectEntityRequest.getEmail())){
            objectEntity.setEmail(objectEntityRequest.getEmail());
        }
        if(!StringUtils.isEmpty(objectEntityRequest.getObjectName())){
            objectEntity.setObjectName(objectEntityRequest.getObjectName());
        }
        objectEntity.setUpdatedBy("default");
        objectEntity.setUpdatedTime(formattedDate);
        return objectRepo.save(objectEntity);
    }

    @Override
    public void deleteObjects(Long objectId) {
        if (!objectRepo.existsById(objectId)) {
            throw new NotFoundException("dashboard with ID " + objectId + " not found");
        }
        objectRepo.deleteById(objectId);
    }
}
