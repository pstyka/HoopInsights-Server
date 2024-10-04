package com.example.backend.team.service;

import com.example.backend.team.dto.TeamAbbAndNameDTO;
import com.example.backend.team.dto.TeamApiResponseDTO;
import com.example.backend.team.entity.Conference;
import com.example.backend.team.entity.Division;
import com.example.backend.team.entity.Team;
import com.example.backend.team.mapper.TeamAbbAndNameMapper;
import com.example.backend.team.mapper.TeamMapper;
import com.example.backend.team.repository.ConferenceRepository;
import com.example.backend.team.repository.DivisionRepository;
import com.example.backend.team.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamsService {

    private final TeamRepository teamRepository;
    private final TeamAbbAndNameMapper teamAbbAndNameMapper;
    private final TeamMapper teamMapper;
    private final ConferenceRepository conferenceRepository;
    private final DivisionRepository divisionRepository;

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

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public List<TeamApiResponseDTO.TeamDTO> getTeams(String name, String conferenceName, String divisionName) {
        List<Team> teams = new ArrayList<>();

        Optional<Conference> conferenceOptional = conferenceRepository.findByNameIgnoreCase(conferenceName);
        Optional<Division> divisionOptional = divisionRepository.findByNameIgnoreCase(divisionName);

        Long conferenceId = conferenceOptional.map(Conference::getId).orElse(null);
        Long divisionId = divisionOptional.map(Division::getId).orElse(null);

        if (StringUtils.hasText(name) && conferenceId == null && divisionId == null) {
            teams = teamRepository.findByNameIsLikeIgnoreCase(name);
        } else if (name == null && conferenceId != null && divisionId == null) {
            teams = teamRepository.findByConferenceId(conferenceId);
        } else if (name == null && conferenceId == null && divisionId != null) {
            teams = teamRepository.findByDivisionId(divisionId);
        } else if (StringUtils.hasText(name) && conferenceId != null && divisionId == null) {
            teams = teamRepository.findByNameIsLikeIgnoreCaseAndConferenceId(name, conferenceId);
        } else if (StringUtils.hasText(name) && conferenceId == null && divisionId != null) {
            teams = teamRepository.findByNameIsLikeIgnoreCaseAndDivisionId(name, divisionId);
        } else if (name == null && conferenceId != null && divisionId != null) {
            teams = teamRepository.findByConferenceIdAndDivisionId(conferenceId, divisionId);
        } else if (StringUtils.hasText(name) && conferenceId != null && divisionId != null) {
            teams = teamRepository.findByNameIsLikeIgnoreCaseAndConferenceIdAndDivisionId(name, conferenceId, divisionId);
        } else {
            teams = teamRepository.findAll();
        }

        return teams.stream().map(teamMapper::teamToTeamDto).collect(Collectors.toList());
    }

    private List<Team> findTeamsByName(String name) {
        return teamRepository.findByNameIsLikeIgnoreCase(name);
    }

    private List<Team> findTeamsByConference(Long conferenceId) {
        return teamRepository.findByConferenceId(conferenceId);
    }

    private List<Team> findTeamsByDivision(Long divisionId) {
        return teamRepository.findByDivisionId(divisionId);
    }

    private List<Team> findTeamsByNameAndConference(String name, Long conferenceId) {
        return teamRepository.findByNameIsLikeIgnoreCaseAndConferenceId(name, conferenceId);
    }

    private List<Team> findTeamsByNameAndDivision(String name, Long divisionId) {
        return teamRepository.findByNameIsLikeIgnoreCaseAndDivisionId(name, divisionId);
    }

    private List<Team> findTeamsByConferenceAndDivision(Long conferenceId, Long divisionId) {
        return teamRepository.findByConferenceIdAndDivisionId(conferenceId, divisionId);
    }

    private List<Team> findTeamsByNameAndConferenceAndDivision(String name, Long conferenceId, Long divisionId) {
        return teamRepository.findByNameIsLikeIgnoreCaseAndConferenceIdAndDivisionId(name, conferenceId, divisionId);
    }
}
