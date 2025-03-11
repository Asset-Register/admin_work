package com.project.ITAM.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeRequest {

    private BgColor bgColor;

    private String selectedColor;

    private String selectedShade; // Store actual file path

    private String isCustom;


}

