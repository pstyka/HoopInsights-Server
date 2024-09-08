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
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "position", nullable = false, length = 20)
    private String position;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "height_inch", nullable = false, length = 10)
    private String heightInch;

    @Column(name = "height_cm", nullable = false, length = 10)
    private String heightCm;

    @Column(name = "weight_lbs", nullable = false, length = 10)
    private String weightLbs;

    @Column(name = "weight_kg", nullable = false, length = 10)
    private String weightKg;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "hand", nullable = false)
    private String hand;

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
