package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "Theme")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bg_color_id") // This column is used to store the reference to the BgColor entity
    private BgColor bgColor;

    @Column(name="selectedColor")
    private String selectedColor;

    @Column(name="selectedShade")
    private String selectedShade; // Store actual file path

    @Column(name="isCustom")
    private String isCustom;

    public Theme() {
    }

    public Theme(Long id, BgColor bgColor, String selectedColor, String selectedShade, String isCustom) {
        this.id = id;
        this.bgColor = bgColor;
        this.selectedColor = selectedColor;
        this.selectedShade = selectedShade;
        this.isCustom = isCustom;
    }
}

