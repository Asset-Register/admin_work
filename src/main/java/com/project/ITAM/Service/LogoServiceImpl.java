package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.LogoEntity;
import com.project.ITAM.Model.LogoRequest;
import com.project.ITAM.Repository.LogoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.List;

@Service
public class LogoServiceImpl implements LogoService{

    @Autowired
    private LogoRepo logoRepo;

    @Override
    public LogoEntity createLogo(String name, MultipartFile file) throws IOException {

        return logoRepo.save(LogoEntity.builder().name(name).image(file.getBytes()).build());
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
    public LogoEntity updateLogo(Long id, String name, MultipartFile file) throws IOException {
        LogoEntity logoEntity=logoRepo.findById(id).orElseThrow(()-> new NotFoundException("logo id not found"));
        // Update name if provided
        if (name != null && !name.isEmpty()) {
            logoEntity.setName(name);
        }

        // Update image if file is provided
        if (file != null && !file.isEmpty()) {
            logoEntity.setImage(file.getBytes());
        }
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
