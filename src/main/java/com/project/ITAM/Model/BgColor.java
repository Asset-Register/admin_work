package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "BgColor")
public class BgColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="backgroundColor")
    private String backgroundColor;

    @Column(name="textColor")
    private String textColor;

    @Column(name="textWhite")
    private String textWhite;

    @Column(name="textBlack")
    private String textBlack;

    @Column(name="layoutTextColor")
    private String layoutTextColor;

    @OneToOne(mappedBy = "bgColor")
    private Theme theme;

    public BgColor() {
    }

    public BgColor(Long id, String backgroundColor, String textColor, String textWhite, String textBlack, String layoutTextColor, Theme theme) {
        this.id = id;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.textWhite = textWhite;
        this.textBlack = textBlack;
        this.layoutTextColor = layoutTextColor;
        this.theme = theme;
    }
}

