package com.project.ITAM.Controller;

import com.project.ITAM.Model.LogoEntity;
import com.project.ITAM.Model.LogoRequest;
import com.project.ITAM.Service.LogoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/logo")
@Validated
@CrossOrigin
public class LogoController {

    @Autowired
            private LogoService logoService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public ResponseEntity<LogoEntity> addLogo(@Valid @RequestBody String name, MultipartFile file) throws IOException {
        return ResponseEntity.ok(logoService.createLogo(name,file));
    }

    @PatchMapping("/{logoId}/update")
    public ResponseEntity<LogoEntity> updateLogo(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,@PathVariable("logoId") Long logoId) throws IOException {
        return ResponseEntity.ok(logoService.updateLogo(logoId,name,file));
    }

    @DeleteMapping("/{logoId}/delete")
    public String deleteLogo(@PathVariable("logoId") Long id){
        logoService.deleteLogo(id);
        return "logo deleted";
    }

    @GetMapping("/{logoId}/get")
    public ResponseEntity<LogoEntity> getUser(@PathVariable("logoId") Long id){
     //   String user= secureUtil.getLoginUser(userDetails);
        return ResponseEntity.ok(logoService.getLogo(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LogoEntity>> getAllLogos(){
        return  ResponseEntity.ok(logoService.getAllLogos());
    }

}
