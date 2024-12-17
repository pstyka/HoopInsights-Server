package com.example.backend.team.controller;

import com.example.backend.team.dto.TeamDTO;
import com.example.backend.team.dto.TeamViewDTO;
import com.example.backend.team.mapper.TeamMapper;
import com.example.backend.team.service.TeamsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/teams")
@AllArgsConstructor
public class TeamController {

    private final TeamsService teamsService;

    @GetMapping("/get-teams")
    private List<TeamDTO> getTeams(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String conference,
                                   @RequestParam(required = false) String division) {
        log.debug("**********| Get all teams |*************");
        return teamsService.getTeams(name,conference,division);
    }

    @GetMapping("/{teamId}")
    public TeamViewDTO getTeamById(@PathVariable Long teamId) {
        return teamsService.getTeamById(teamId);
    }

}
