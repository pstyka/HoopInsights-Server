package com.example.backend.player.dto;

import com.example.backend.standings.dto.TeamStandingsDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalaryAnalysisDTO {

    private String firstName;
    private String lastName;
    private String position;
    private String teamName;
    private Integer salary;
    private PlayerSeasonStatsDTO seasonStats;
    private TeamStandingsDTO standings;


}
