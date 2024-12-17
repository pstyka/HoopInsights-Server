package com.example.backend.player.dto;

import com.example.backend.shoe.entity.Shoe;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private String hand;
    private Date birthDate;
    private String country;
    private String heightInch;
    private String heightCm;
    private String weightLbs;
    private String weightKg;
    private String photoUrl;
    private String draft;
    private String jerseyNumber;
    private Long apiId;
    private Boolean isActive;
    private String teamName;
    private String teamLogoUrl;
    private Shoe currentShoe;

}

