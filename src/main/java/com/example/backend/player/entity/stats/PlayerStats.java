package com.example.backend.player.entity.stats;

import com.example.backend.player.entity.Player;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "player_stats")
public abstract class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "games_played", nullable = false)
    private int gamesPlayed;

    @Column(name = "games_started", nullable = false)
    private int gamesStarted;

    @Column(name = "minutes_played", nullable = false)
    private double minutesPlayed;

    @Column(name = "field_goals", nullable = false)
    private double fieldGoals;

    @Column(name = "field_goals_attempts", nullable = false)
    private double fieldGoalsAttempts;

    @Column(name = "field_goals_percent", nullable = false)
    private double fieldGoalsPercent;

    @Column(name = "three_pointers_made", nullable = false)
    private double threePointersMade;

    @Column(name = "three_pointers_attempts", nullable = false)
    private double threePointersAttempts;

    @Column(name = "three_pointers_percent", nullable = false)
    private double threePointersPercent;

    @Column(name = "two_pointers_attempts", nullable = false)
    private double twoPointersAttempts;

    @Column(name = "two_pointers_percent", nullable = false)
    private double twoPointersPercent;

    @Column(name = "free_throw_attempts", nullable = false)
    private double freeThrowAttempts;

    @Column(name = "free_throw_percent", nullable = false)
    private double freeThrowPercent;

    @Column(name = "offensive_rebounds", nullable = false)
    private double offensiveRebounds;

    @Column(name = "defensive_rebounds", nullable = false)
    private double defensiveRebounds;

    @Column(name = "rebounds", nullable = false)
    private double rebounds;

    @Column(name = "assists", nullable = false)
    private double assists;

    @Column(name = "steals", nullable = false)
    private double steals;

    @Column(name = "blocks", nullable = false)
    private double blocks;

    @Column(name = "turnovers", nullable = false)
    private double turnovers;

    @Column(name = "fouls", nullable = false)
    private double fouls;

    @Column(name = "points", nullable = false)
    private double points;

}
