package com.project.ITAM.Service;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Model.ObjectEntity;
import com.project.ITAM.Model.ObjectEntityRequest;

import java.util.List;

public interface ObjectEntityService {

    public ObjectEntity createObjectEntity(ObjectEntityRequest objectEntityRequest);

    public List<ObjectEntity> getAllObjects();

    public ObjectEntity getObjectById(Long objectId);

    public ObjectEntity updateObjects(Long id, ObjectEntityRequest objectEntityRequest);

    public void deleteObjects(Long id);
}
