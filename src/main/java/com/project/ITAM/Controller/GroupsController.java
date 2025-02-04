package com.project.ITAM.Controller;

import com.project.ITAM.Model.GroupRequest;
import com.project.ITAM.Model.Groups;
import com.project.ITAM.Model.Permission;
import com.project.ITAM.Repository.GroupRepo;
import com.project.ITAM.Repository.UserRepo;
import com.project.ITAM.Service.GroupsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin
@Validated
public class GroupsController {

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
            private GroupsService groupsService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public ResponseEntity<Groups> addGroup(@Valid @RequestBody GroupRequest groupRequest){
        return ResponseEntity.ok(groupsService.createGroups(groupRequest));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Groups> updateGroup(@RequestBody GroupRequest groupRequest,@PathVariable Long id){
        return ResponseEntity.ok(groupsService.updateGroupById(groupRequest,id));
    }

    @DeleteMapping("/{id}/delete")
    public String deletegroup(@PathVariable Long id){
        groupsService.deleteGroupById(id);
        if(!groupRepo.existsById(id)){
            return "group deleted";
        }else{
            return "group not deleted";
        }
    }

    @GetMapping("/{id}/read")
    public ResponseEntity<Groups> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupsService.getGroupById(id));
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<Groups>> getAllGroups(){
        return  ResponseEntity.ok(groupsService.getAllGroups());
    }

}
