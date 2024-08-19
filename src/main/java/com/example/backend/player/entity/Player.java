package com.example.backend.player.entity;

import com.example.backend.player.entity.stats.PlayerCareerStats;
import com.example.backend.player.entity.stats.PlayerSeasonStats;
import com.example.backend.team.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private Date birthDate;
    private String heightInch;
    private String weightLbs;
    private String country;
    private int jerseyNumber;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerSeasonStats> playerSeasonStats = new ArrayList<>();

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private PlayerCareerStats playerCareerStats;
}
