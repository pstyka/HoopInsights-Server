package com.example.backend.player.controller;

import com.example.backend.player.dto.BestPlayerForAnalysisDTO;
import com.example.backend.player.dto.PlayerSeasonStatsDTO;
import com.example.backend.player.service.PlayerStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerStatsController {

    private final PlayerStatsService playerStatsService;

    public PlayerStatsController(PlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    @GetMapping("/{playerId}/stats")
    public ResponseEntity<List<PlayerSeasonStatsDTO>> getPlayerSeasonStats(@PathVariable Long playerId) {
        List<PlayerSeasonStatsDTO> stats = playerStatsService.getPlayerSeasonStatsByPlayerId(playerId);
        if (stats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/this-season-stats")
    public ResponseEntity<List<BestPlayerForAnalysisDTO>> getAllPlayerThisSeasonStats() {
        List<BestPlayerForAnalysisDTO> stats = playerStatsService.getAllPlayerStatsForCurrentSeason();
        if (stats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stats);
    }

}