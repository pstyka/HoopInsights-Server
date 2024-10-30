package com.example.backend.standings.repository;

import com.example.backend.standings.entity.TeamStandings;
import com.example.backend.team.entity.Conference;
import com.example.backend.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamStandingsRepository extends JpaRepository<TeamStandings, Long> {
    Optional<TeamStandings> findByTeamAndConference(Team team, Conference conference);
    List<TeamStandings> findByConference(Conference conference);
}
