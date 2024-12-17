package com.example.backend.player.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InjuryAnalysisDTO {
    public String firstName;
    public String lastName;
    public Date birthDate;
    public String heightInch;
    public String heightCm;
    public String weightLbs;
    public String weightKg;

    public String teamName;

    public String shoeBrand;
    public String shoeModel;
    public String shoeType;

    public String position;
    public LocalDate updated;
    public String injury;
    public String injuryStatus;

}
