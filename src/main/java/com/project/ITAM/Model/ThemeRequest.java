package com.project.ITAM.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ThemeRequest {

    private BgColor bgColor;

    private String selectedColor;

    private String selectedShade; // Store actual file path

    private String isCustom;

}

