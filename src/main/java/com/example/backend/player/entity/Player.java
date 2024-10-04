package com.example.backend.player.entity;

import com.example.backend.player.entity.stats.PlayerCareerStats;
import com.example.backend.player.entity.stats.PlayerSeasonStats;
import com.example.backend.team.entity.Team;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "api_id")
    private Long apiId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "position")
    private String position;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "height_inch", length = 10)
    private String heightInch;

    @Column(name = "height_cm", length = 10)
    private String heightCm;

    @Column(name = "weight_lbs", length = 10)
    private String weightLbs;

    @Column(name = "weight_kg", length = 10)
    private String weightKg;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "hand")
    private String hand;

    @Column(name = "jersey_number", nullable = false, length = 3)
    private String jerseyNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("season ASC")
    private List<PlayerSeasonStats> playerSeasonStats = new ArrayList<>();

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private PlayerCareerStats playerCareerStats;



}
