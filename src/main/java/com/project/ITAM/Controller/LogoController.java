package com.project.ITAM.Controller;

import com.project.ITAM.Model.LogoEntity;
import com.project.ITAM.Model.LogoRequest;
import com.project.ITAM.Model.LogoResponse;
import com.project.ITAM.Service.LogoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logo")
@Validated
@CrossOrigin
public class LogoController {

    @Autowired
            private LogoService logoService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public ResponseEntity<LogoEntity> addLogo(@Valid @RequestParam("name") String name, @RequestParam("file") MultipartFile file) throws IOException {
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
    public ResponseEntity<byte[]> getUser(@PathVariable("logoId") Long id){
        LogoEntity logoEntity = logoService.getLogo(id);
        if (logoEntity.getImage() != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/png") // Change "image/png" based on file type
                    .body(logoEntity.getImage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<byte[]>> getAllLogos(){
        List<byte[]> images = logoService.getAllLogos()
                .stream()
                .map(LogoEntity::getImage) // Get image byte array
                .collect(Collectors.toList());
        return ResponseEntity.ok(images);
    }




}
