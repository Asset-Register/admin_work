package com.project.ITAM.Service;


import com.project.ITAM.Model.Theme;
import com.project.ITAM.Model.ThemeRequest;

import java.util.List;

public interface ThemeService {

    public Theme createTheme(ThemeRequest themeRequest);
    public Theme updateTheme(ThemeRequest themeRequest,Long themeId);
    public void deleteTheme(Long themeId);
    public List<Theme> getAllTheme();
    public Theme getThemeById(Long themeId);
}
