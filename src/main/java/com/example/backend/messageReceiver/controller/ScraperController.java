package com.example.backend.messageReceiver.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/scraper")
@RequiredArgsConstructor
@Slf4j
public class ScraperController {

    private final RestTemplate restTemplate;

    @GetMapping("/scrape-players")
    public ResponseEntity<String> scrapePlayers() {
        String flaskUrl = "http://flask-service:5000/scrape-players";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, null, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }


}
