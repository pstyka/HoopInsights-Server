package com.example.backend.player.controller;

import com.example.backend.player.service.PlayerApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/players")
@AllArgsConstructor
public class PlayerApiController {

    private final PlayerApiService playerApiService;

    @PutMapping("/update-players")
    public ResponseEntity<String> updatePlayersForTeam(
            @RequestParam("teamId") Integer teamId,
            @RequestParam("season") String season) {
        try {
            playerApiService.savePlayersForTeam(season, teamId);
            return ResponseEntity.ok("Players updated successfully for team API ID: " + teamId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating players: " + e.getMessage());
        }
    }
    @PutMapping("/update-by-year")
    public ResponseEntity<String> updatePlayersForTeam(
            @RequestParam("season") String season) {
        try {
            playerApiService.getAllPlayersByYear(season);
            return ResponseEntity.ok("Players updated successfully for year " + season);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating players: " + e.getMessage());
        }
    }

}
