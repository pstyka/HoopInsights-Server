package com.example.backend.player.dto;

import com.example.backend.player.entity.Player;
import com.example.backend.team.entity.Team;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class SalaryDTO {
    private String firstName;
    private String lastName;
    private String team;
    private Integer salary;
}
