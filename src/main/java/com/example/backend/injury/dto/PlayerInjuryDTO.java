package com.example.backend.injury.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerInjuryDTO {
     String firstName;
     String lastName;
     String abb;
     String position;
     LocalDate updated;
     String injury;
     String injuryStatus;
}
