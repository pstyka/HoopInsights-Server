package com.example.backend.standings.controller;

import com.example.backend.standings.dto.TeamStandingsDTO;
import com.example.backend.standings.entity.TeamStandings;
import com.example.backend.standings.service.TeamStandingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/team-standings")
@AllArgsConstructor
public class TeamStandingsController {
    private final TeamStandingsService teamStandingsService;

    @PostMapping("/update")
    public ResponseEntity<String> updateStandings() {
        try {
            teamStandingsService.saveOrUpdateStandings();
            return ResponseEntity.ok("Standings updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update standings: " + e.getMessage());
        }
    }

    @GetMapping("/get-team-standings")
    public ResponseEntity<List<TeamStandingsDTO>> getStandings() {
        List<TeamStandingsDTO> standings = teamStandingsService.getStandings();
        return ResponseEntity.ok(standings);
    }
}
