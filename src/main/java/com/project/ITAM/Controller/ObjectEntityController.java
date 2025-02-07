package com.project.ITAM.Controller;

import com.project.ITAM.Model.ObjectEntity;
import com.project.ITAM.Model.ObjectEntityRequest;
import com.project.ITAM.Service.ObjectEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/objects")
@CrossOrigin
@Validated
public class ObjectEntityController {

    @Autowired
    private ObjectEntityService objectEntityService;

    @PostMapping("/add")
    public ResponseEntity<ObjectEntity> createObjects(@RequestBody ObjectEntityRequest objectEntityRequest) {
        return ResponseEntity.ok(objectEntityService.createObjectEntity(objectEntityRequest));
    }

    @GetMapping("/{object_id}/get")
    public ResponseEntity<ObjectEntity> getObjectById(@PathVariable("object_id") Long objectId) {
        return ResponseEntity.ok(objectEntityService.getObjectById(objectId));
    }

    @PatchMapping("/{object_id}/update")
    public ResponseEntity<ObjectEntity> updateObjects(@PathVariable("object_id") Long objectId,@RequestBody ObjectEntityRequest objectEntityRequest){
        return ResponseEntity.ok(objectEntityService.updateObjects(objectId,objectEntityRequest));
    }

    @DeleteMapping("/{object_id}/delete")
    public String deleteByObjectId(@PathVariable("object_id") Long objectId){
        objectEntityService.deleteObjects(objectId);
        return "Object deleted" +objectId;
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<ObjectEntity>> getAllObjects(){
        return  ResponseEntity.ok(objectEntityService.getAllObjects());
    }

}
