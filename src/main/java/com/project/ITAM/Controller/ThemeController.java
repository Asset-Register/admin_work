package com.project.ITAM.Controller;

import com.project.ITAM.Model.Theme;
import com.project.ITAM.Model.ThemeRequest;
import com.project.ITAM.Service.ThemeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/theme")
@Validated
@CrossOrigin
public class ThemeController {

    @Autowired
            private ThemeService themeService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public ResponseEntity<Theme> addUser(@Valid @RequestBody ThemeRequest themeRequest){
        return ResponseEntity.ok(themeService.createTheme(themeRequest));
    }

    @PatchMapping("/{theme_id}/update")
    public ResponseEntity<Theme> updateUser(@RequestBody ThemeRequest themeRequest,@PathVariable("theme_id") Long themeId){
        return ResponseEntity.ok(themeService.updateTheme(themeRequest,themeId));
    }

    @DeleteMapping("/{theme_id}/delete")
    public String deleteUser(@PathVariable("theme_id") Long id){
        themeService.deleteTheme(id);
        return "theme deleted";
    }

    @GetMapping("/{theme_id}/read")
    public ResponseEntity<Theme> getUser(@PathVariable("theme_id") Long id){
     //   String user= secureUtil.getLoginUser(userDetails);
        return ResponseEntity.ok(themeService.getThemeById(id));
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<Theme>> getAllUsers(){
        return  ResponseEntity.ok(themeService.getAllTheme());
    }

}
