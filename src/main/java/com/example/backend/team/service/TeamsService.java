package com.example.backend.team.service;

import com.example.backend.team.dto.TeamAbbAndNameDTO;
import com.example.backend.team.entity.Team;
import com.example.backend.team.mapper.TeamAbbAndNameMapper;
import com.example.backend.team.mapper.TeamMapper;
import com.example.backend.team.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamsService {

    private final TeamRepository teamRepository;
    private final TeamAbbAndNameMapper teamAbbAndNameMapper;
    private final TeamMapper teamMapper;

    public void saveAllTeams(List<Team> teams) {
        teamRepository.saveAll(teams);
    }

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    public List<TeamAbbAndNameDTO> getTeamsAbbList() {
        List<TeamAbbAndNameDTO> teams = new ArrayList<>();
        for (Team team : teamRepository.findAll()) {
            teams.add(teamAbbAndNameMapper.teamToTeamDto(team));
        }
        return teams;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Integer> getTeamApiIds() {
        return teamRepository.findAll().stream().map(Team::getApiId).collect(Collectors.toList());
    }


}
