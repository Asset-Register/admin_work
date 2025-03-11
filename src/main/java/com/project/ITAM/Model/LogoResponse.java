package com.project.ITAM.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoResponse {
    private Long id;
    private String name;
    private String imageUrl;
}
