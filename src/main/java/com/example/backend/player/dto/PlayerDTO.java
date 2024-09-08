package com.example.backend.player.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
    private String firstName;
    private String lastName;
    private String position;
    private String hand;
    private String birthDate;
    private String heightInch;
    private String heightCm;
    private String weightLbs;
    private String weightKg;
    private Boolean isActive;
}

