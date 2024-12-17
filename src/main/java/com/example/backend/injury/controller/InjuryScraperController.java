package com.example.backend.injury.controller;

import com.example.backend.injury.repository.InjuryRepository;
import com.example.backend.injury.service.InjuryScraperService;
import com.example.backend.injury.service.InjuryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/injuries")
public class InjuryScraperController {

    @Autowired
    private InjuryScraperService injuryScraperService;

    @Autowired
    private InjuryService injuryService;



    @PostMapping("/scrape-injuries")
    public ResponseEntity<String> scrapeInjuries() {
        try {
            injuryService.clearInjuries();
            String response = injuryScraperService.scrapeInjuries();
            return ResponseEntity.ok("Scraping started. Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Flask error: " + e.getMessage());
        }
    }
}
