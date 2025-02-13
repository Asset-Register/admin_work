package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.BgColor;
import com.project.ITAM.Model.Theme;
import com.project.ITAM.Model.ThemeRequest;
import com.project.ITAM.Repository.ThemeRepo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeRepo themeRepo;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    LocalDateTime updateddate = LocalDateTime.now(ZoneId.systemDefault());
    String formattedDate = updateddate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Override
    public Theme createTheme(ThemeRequest themeRequest) {
        if (themeRequest.getBgColor() != null) {
            return themeRepo.save(Theme.builder().createdBy("default").createdTime(formattedDate)
                    .bgColor(BgColor.builder().backgroundColor(themeRequest.getBgColor().getBackgroundColor())
                            .textColor(themeRequest.getBgColor().getTextColor()).layoutTextColor(themeRequest.getBgColor().getLayoutTextColor())
                            .textBlack(themeRequest.getBgColor().getTextBlack()).textWhite(themeRequest.getBgColor().getTextWhite()).build())
                    .isCustom(themeRequest.getIsCustom())
                    .selectedShade(themeRequest.getSelectedShade()).selectedColor(themeRequest.getSelectedColor()).build());
        } else {
            return themeRepo.save(Theme.builder().createdBy("default").createdTime(formattedDate)
                    .isCustom(themeRequest.getIsCustom())
                    .selectedShade(themeRequest.getSelectedShade()).selectedColor(themeRequest.getSelectedColor()).build());
        }
    }

    @Override
    public Theme updateTheme(ThemeRequest themeRequest, Long themeId) {
        Theme theme = themeRepo.findById(themeId)
                .orElseThrow(() -> new NotFoundException(themeId + " not found"));
        if(!StringUtils.isEmpty(themeRequest.getIsCustom())) {
            theme.setIsCustom(themeRequest.getIsCustom());
        }
        if(!StringUtils.isEmpty(themeRequest.getSelectedColor())) {
            theme.setSelectedColor(themeRequest.getSelectedColor());
        }
        if(!StringUtils.isEmpty(themeRequest.getSelectedShade())) {
            theme.setSelectedShade(themeRequest.getSelectedShade());
        }
        if(!ObjectUtils.isEmpty(themeRequest.getBgColor())) {
            BgColor bgColor = new BgColor();
            if(!StringUtils.isEmpty(themeRequest.getBgColor().getBackgroundColor())) {
                bgColor.setBackgroundColor(themeRequest.getBgColor().getBackgroundColor());
            }
            if(!StringUtils.isEmpty(themeRequest.getBgColor().getTextColor())) {
                bgColor.setTextColor(themeRequest.getBgColor().getTextColor());
            }
            if(!StringUtils.isEmpty(themeRequest.getBgColor().getTextWhite())) {
                bgColor.setTextWhite(themeRequest.getBgColor().getTextWhite());
            }
            if(!StringUtils.isEmpty(themeRequest.getBgColor().getTextBlack())) {
                bgColor.setTextBlack(themeRequest.getBgColor().getTextBlack());
            }
            if(!StringUtils.isEmpty(themeRequest.getBgColor().getLayoutTextColor())) {
                bgColor.setLayoutTextColor(themeRequest.getBgColor().getLayoutTextColor());
            }
            theme.setBgColor(bgColor);
        }
         theme.setUpdatedBy("default");
        theme.setUpdatedTime(formattedDate);
        return themeRepo.save(theme);
    }

    @Override
    public void deleteTheme(Long themeId) {
         if(themeRepo.existsById(themeId)){
             themeRepo.deleteById(themeId);
         }
    }

    @Override
    public List<Theme> getAllTheme() {
        return themeRepo.findAll();
    }

    @Override
    public Theme getThemeById(Long themeId) {
        return themeRepo.findById(themeId)
                .orElseThrow(() -> new NotFoundException(themeId + " not found"));
    }

}
