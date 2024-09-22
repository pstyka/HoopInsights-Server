package com.example.backend.team.controller;

import com.example.backend.team.service.TeamsApiService;
import com.example.backend.team.service.TeamsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
@AllArgsConstructor
public class TeamApiController {

    private final TeamsApiService teamsApiService;
    private final TeamsService teamsService;


    @PostMapping("/fetch")
    public ResponseEntity<String> fetchTeams(@RequestParam String abbreviation) {
        teamsApiService.saveTeamsByAbbreviation(abbreviation);
        return ResponseEntity.ok("Teams saved successfully.");
    }
    @PutMapping("/update-all-teams")
    public ResponseEntity<String> updateAllTeams() {
        teamsApiService.updateTeamsFromApi();
        return ResponseEntity.ok("Teams updated successfully.");
    }

    @PutMapping("/update-by-abb")
    public ResponseEntity<String> updateTeamByAbb(@RequestParam String abb) {
        try {
            teamsApiService.updateTeamByAbbFromApi(abb);
            return ResponseEntity.ok("Team updated successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update team: " + e.getMessage());
        }
    }


}
