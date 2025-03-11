package com.project.ITAM.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "Logo")
public class LogoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logoId;

    @Column
    private String name;

    @Lob
    @Column
    private byte[] image;

}
