package com.example.backend.team.entity;

import com.example.backend.standings.entity.TeamStandings;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "conference")
    private Set<Division> divisions;

    @OneToMany(mappedBy = "conference")
    private Set<Team> teams;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<TeamStandings> teamStandings = new ArrayList<>();

}
