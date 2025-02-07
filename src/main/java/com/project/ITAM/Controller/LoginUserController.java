/*
package com.project.ITAM.Controller;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/login")
@CrossOrigin
@Validated
public class LoginUserController {

    @Autowired
    private DashBoardService dashBoardService;

    @PostMapping("/create")
    public ResponseEntity<LoginUserEntity> createLoginUser(@RequestBody LoginUserRequest loginUserRequest) {
        return ResponseEntity.ok(dashBoardService.uploadDashBoard(dashBoard));
    }

    @GetMapping("/{login_id}/read")
    public ResponseEntity<LoginUserEntity> getLoginUserDetails(@PathVariable("login_id") Long loginId) {
        return ResponseEntity.ok(dashBoardService.getDashboardInFolder(folderId));
    }

    @PatchMapping("/{login_id}/update")
    public ResponseEntity<DashBoard> updateLoginDetail(@PathVariable("login_id") Long loginId,@RequestBody LoginUserRequest loginUserRequest){
        return ResponseEntity.ok(dashBoardService.updatedashBoard(dashBoardId,dashBoardRequest));
    }

    @DeleteMapping("/{login_id}/delete")
    public String deleteByLoginId(@PathVariable("login_id") Long loginId){
        dashBoardService.deleteDashBoard(dashBoardId);
        return "dashBoard deleted";
    }

}
*/
