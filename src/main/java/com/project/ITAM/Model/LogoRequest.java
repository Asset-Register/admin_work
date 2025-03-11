package com.project.ITAM.Model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogoRequest {

    private String name;

    private byte[] image;

}

