package com.example.backend.team.entity;

import com.example.backend.player.entity.Player;
import com.example.backend.player.entity.salary.Salary;
import com.example.backend.standings.entity.TeamStandings;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String abb;
    private String city;
    private String logoUrl;
    private Integer apiId;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @OneToMany(mappedBy = "team")
    private Set<Player> players;

    @OneToMany(mappedBy = "team")
    private List<Salary> salaries;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL)
    private TeamStandings teamStandings;
}
