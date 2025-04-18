package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.LogoEntity;
import com.project.ITAM.Model.LogoRequest;
import com.project.ITAM.Repository.LogoRepo;
import com.project.ITAM.helper.DateTimeUtil;
import com.project.ITAM.helper.ExtractJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LogoServiceImpl implements LogoService{

    @Autowired
    private LogoRepo logoRepo;

    @Override
    public LogoEntity createLogo(String name, MultipartFile file,String filePath) throws IOException {
        if (file != null && !file.isEmpty()) {
            return logoRepo.save(LogoEntity.builder().name(name).image(file.getBytes()).createdBy(ExtractJsonUtil.getUserdetails())
                    .filePath(filePath)
                    .createdTime(DateTimeUtil.currentDateTime()).build());
        } else {
            return logoRepo.save(LogoEntity.builder().name(name).createdBy(ExtractJsonUtil.getUserdetails())
                    .filePath(filePath)
                    .createdTime(DateTimeUtil.currentDateTime()).build());
        }
    }
    @Override
    public LogoEntity getLogo(Long logoId) {
       return logoRepo.findById(logoId).orElseThrow(()-> new NotFoundException("logo id not found"));
    }

    @Override
    public List<LogoEntity> getAllLogos() {
        return logoRepo.findAll();
    }

    @Override
    public LogoEntity updateLogo(Long id, String name, MultipartFile file,String filePath) throws IOException {
        LogoEntity logoEntity=logoRepo.findById(id).orElseThrow(()-> new NotFoundException("logo id not found"));
        // Update name if provided
        if (StringUtils.isEmpty(name)) {
            logoEntity.setName(name);
        }

        if (StringUtils.isEmpty(filePath)) {
            logoEntity.setFilePath(filePath);
        }

        // Update image if file is provided
        if (file != null && !file.isEmpty()) {
            logoEntity.setImage(file.getBytes());
        }
        logoEntity.setUpdatedBy(ExtractJsonUtil.getUserdetails());
        logoEntity.setUpdatedTime(DateTimeUtil.currentDateTime());
        return logoRepo.save(logoEntity);
    }

    @Override
    public void deleteLogo(Long id) {
     if(!logoRepo.existsById(id)){
         throw new NotFoundException("dashboard with ID " + id + " not found");
     }
     logoRepo.deleteById(id);
    }
}
