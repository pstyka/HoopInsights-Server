package com.example.backend.standings.entity;

import com.example.backend.team.entity.Conference;
import com.example.backend.team.entity.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamStandings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int wins;
    private int losses;
    private double winPercentage;
    private String gamesBehind;
    private double pointsPerGame;
    private double opponentPointsPerGame;
    private double simpleRatingSystem;

    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = false)
    private Conference conference;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
