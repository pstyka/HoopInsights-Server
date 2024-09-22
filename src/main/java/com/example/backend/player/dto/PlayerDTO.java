package com.example.backend.player.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
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
    private Long apiId;
    private Boolean isActive;
}

