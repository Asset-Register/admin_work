package com.project.ITAM.Service;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Model.LogoEntity;
import com.project.ITAM.Model.LogoRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LogoService {

    public LogoEntity createLogo(String name, MultipartFile file) throws IOException;

    public LogoEntity getLogo(Long logoId);


    public List<LogoEntity> getAllLogos();

    public LogoEntity updateLogo(Long id, String name, MultipartFile file) throws IOException;

    public void deleteLogo(Long id);

}
