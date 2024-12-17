package com.example.backend.player.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BestPlayerForAnalysisDTO {
    private String firstName;
    private String lastName;
    private String teamName;
    private PlayerSeasonStatsDTO stats;

}
