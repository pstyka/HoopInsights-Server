package com.example.backend.player.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSeasonStatsDTO {
    private String season;
    private int gamesPlayed;
    private int gamesStarted;
    private double minutesPlayed;
    private double fieldGoals;
    private double fieldGoalsAttempts;
    private double fieldGoalsPercent;
    private double threePointersMade;
    private double threePointersAttempts;
    private double threePointersPercent;
    private double twoPointersAttempts;
    private double twoPointersPercent;
    private double freeThrowAttempts;
    private double freeThrowPercent;
    private double offensiveRebounds;
    private double defensiveRebounds;
    private double rebounds;
    private double assists;
    private double steals;
    private double blocks;
    private double turnovers;
    private double fouls;
    private double points;
}
