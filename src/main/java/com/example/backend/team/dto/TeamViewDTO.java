package com.example.backend.team.dto;

import com.example.backend.player.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamViewDTO {
    private Long id;
    private String name;
    private String abb;
    private String city;
    private String logoUrl;
    private String divisionName;
    private String conferenceName;
    private Set<PlayerDTO> players;
}
