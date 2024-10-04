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
    public ResponseEntity<String> scrapePlayersByLetter(@RequestParam String letter, @RequestParam String season) {
        playerScrapingService.scrapeAndSavePlayers(letter,season);
        return ResponseEntity.ok("Scraping and saving player stats for letter: "+ letter + " and season: " + season);
    }
}
