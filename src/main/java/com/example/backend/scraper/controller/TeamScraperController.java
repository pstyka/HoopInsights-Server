package com.example.backend.scraper.controller;

import com.example.backend.team.dto.TeamAbbAndNameDTO;
import com.example.backend.team.entity.Team;
import com.example.backend.team.mapper.TeamAbbAndNameMapper;
import com.example.backend.team.service.TeamsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/scraper")
@RequiredArgsConstructor
@Slf4j
public class TeamScraperController {

    private final RestTemplate restTemplate;
    private final TeamsService teamsService;
    private final TeamAbbAndNameMapper teamAbbAndNameMapper;

    @GetMapping("/scrape-teams")
    public ResponseEntity<String> scrapeTeams() {
        String flaskUrl = "http://flask-service:5000/scrape-teams";
        try {
            ResponseEntity<TeamAbbAndNameDTO[]> response = restTemplate.postForEntity(flaskUrl, null, TeamAbbAndNameDTO[].class);
            TeamAbbAndNameDTO[] teams = response.getBody();

            if (teams != null && teams.length > 0) {

                for (TeamAbbAndNameDTO teamDTO : teams) {
                    Team team = teamAbbAndNameMapper.teamDtoToTeam(teamDTO);
                    teamsService.saveTeam(team);
                }
                return ResponseEntity.ok("Teams saved successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No teams received");
            }
        } catch (HttpClientErrorException e) {
            log.error("Error occurred while fetching teams from Flask: {}", e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
