package com.example.backend.standings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamStandingsDTO {
    private String teamName;
    private int wins;
    private int losses;
    private double winPercentage;
    private String gamesBehind;
    private double pointsPerGame;
    private double opponentPointsPerGame;
    private double simpleRatingSystem;
    private String conferenceName;
    private String logoUrl;

}
