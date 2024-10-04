package com.example.backend.team.controller;

import com.example.backend.team.dto.TeamApiResponseDTO;
import com.example.backend.team.mapper.TeamMapper;
import com.example.backend.team.service.TeamsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/teams")
@AllArgsConstructor
public class TeamController {

    private final TeamsService teamsService;
    private final TeamMapper teamMapper;

    @GetMapping("/get-teams")
    private List<TeamApiResponseDTO.TeamDTO> getTeams(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String conference,
                                                      @RequestParam(required = false) String division) {
        log.debug("**********| Get all teams |*************");
        return teamsService.getAllTeams().stream().map(teamMapper::teamToTeamDto).collect(Collectors.toList());
    }

}
