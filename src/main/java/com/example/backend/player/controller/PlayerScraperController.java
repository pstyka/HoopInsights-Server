package com.example.backend.player.controller;

import com.example.backend.player.service.PlayerScrapingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
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
    public ResponseEntity<String>  scrapePlayers() {
        playerScrapingService.scrapePlayersFromNbaCom();
        return ResponseEntity.ok("Scraping from NBA.com was succesfully done!");
    }
}
