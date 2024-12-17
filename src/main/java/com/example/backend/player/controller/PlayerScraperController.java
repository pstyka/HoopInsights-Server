package com.example.backend.player.controller;

import com.example.backend.player.service.PlayerScrapingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
@Slf4j
public class PlayerScraperController {

    private final PlayerScrapingService playerScrapingService;

    @PostMapping("/scrape-stats")
    public ResponseEntity<String> scrapePlayersByLetter(@RequestParam String letter) {
        playerScrapingService.scrapeAndSavePlayers(letter);
        return ResponseEntity.ok("Scraping and saving player stats for letter: " + letter);
    }

    @PostMapping("/scrape-stats-for-all-players")
    public ResponseEntity<String> scrapeSeasonStatsForAllActivePlayers(){
        playerScrapingService.scrapeAndSaveAllPlayerStats();
        return ResponseEntity.ok("Scraping and saving players stats was succesful");
    }

    @PostMapping("/scrape-players_nba_com")
    public ResponseEntity<String> scrapePlayers() {
        playerScrapingService.scrapePlayersFromNbaCom();
        return ResponseEntity.ok("Scraping from NBA.com was succesfully done!");
    }

    @PostMapping("/scrape-this-season-stats")
    public ResponseEntity<String> scrapeThisSeasonStats() {

        playerScrapingService.scrapeThisSeasonStats();
        return ResponseEntity.ok("Scraping and saving players stats was succesful");
    }

    @PostMapping("/scrape-this-season-stats-for-player")
    public ResponseEntity<?> scrapeThisSeasonStatsForPlayers() {
        try {
            String response = playerScrapingService.scrapeThisSeasonStatsForPlayers();
            return ResponseEntity.ok(Map.of("message", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/scrape-salaries")
    public ResponseEntity<String> scrapeSalaries() {
        try {
            playerScrapingService.scrapeSalariesFromBasketballReferenceCom();
            return ResponseEntity.ok("Salary scraping triggered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error triggering salary scraping: " + e.getMessage());
        }
    }

}
