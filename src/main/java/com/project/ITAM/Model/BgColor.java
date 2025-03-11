package com.project.ITAM.Model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BgColor {
    private String backgroundColor;
    private String textColor;
    private String textWhite;
    private String textBlack;
    private String layoutTextColor;
    private String theme;
}
