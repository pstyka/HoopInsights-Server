package com.example.backend.team.service;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.team.dto.TeamAbbAndNameDTO;
import com.example.backend.team.dto.TeamDTO;
import com.example.backend.team.dto.TeamViewDTO;
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

    public List<TeamDTO> getTeams(String name, String conferenceName, String divisionName) {
        List<Team> teams;

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
        } else if (name == null && conferenceId != null) {
            teams = teamRepository.findByConferenceIdAndDivisionId(conferenceId, divisionId);
        } else if (StringUtils.hasText(name) && conferenceId != null && divisionId != null) {
            teams = teamRepository.findByNameIsLikeIgnoreCaseAndConferenceIdAndDivisionId(name, conferenceId, divisionId);
        } else {
            teams = teamRepository.findAll();
        }

        return teams.stream().map(teamMapper::teamToTeamDto).collect(Collectors.toList());
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

    public List<Integer> getTeamApiIds() {
        return teamRepository.findAll().stream().map(Team::getApiId).collect(Collectors.toList());
    }

    public TeamViewDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return TeamViewDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .logoUrl(team.getLogoUrl())
                .abb(team.getAbb())
                .city(team.getCity())
                .conferenceName(team.getConference() != null ? team.getConference().getName() : null)
                .divisionName(team.getDivision() != null ? team.getDivision().getName() : null)
                .players(team.getPlayers().stream()
                        .map(player -> PlayerDTO.builder()
                                .id(player.getId())
                                .firstName(player.getFirstName())
                                .lastName(player.getLastName())
                                .position(player.getPosition())
                                .heightInch(player.getHeightInch())
                                .weightLbs(player.getWeightLbs())
                                .birthDate(player.getBirthDate())
                                .build()
                        ).collect(Collectors.toSet()))
                .build();
    }
}
