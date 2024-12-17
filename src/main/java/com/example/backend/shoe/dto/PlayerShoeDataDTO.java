package com.example.backend.shoe.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PlayerShoeDataDTO {
    private String firstName;
    private String lastName;
    private String teamName;
    private String shoeBrand;
    private String shoeModel;
    private String imageUrl;
}

