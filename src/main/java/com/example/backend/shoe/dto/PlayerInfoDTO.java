package com.example.backend.shoe.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerInfoDTO {
    private String height;
    private String weight;
    private String country;
    private LocalDate dateOfBirth;
}
