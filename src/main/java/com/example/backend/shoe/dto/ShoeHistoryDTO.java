package com.example.backend.shoe.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ShoeHistoryDTO {
    private LocalDate dateWorn;
    private String brand;
    private String model;
}
