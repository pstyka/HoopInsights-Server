package com.example.backend.player.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Map;

@Service
public class PlayerScrapingService {

    @Value("${flask.base-url}")
    private String flaskBaseUrl;

    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public PlayerScrapingService(RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void scrapeAndSavePlayers(String letter) {
        String url = flaskBaseUrl + "/scrape-players/" + letter;

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Scraping triggered successfully in Flask.");
            } else {
                throw new RuntimeException("Failed to trigger scraping in Flask");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during scraping: " + e.getMessage());
        }
    }

    public void scrapeAndSaveAllPlayerStats() {
        String letters = "abcdefghijklmnoprstuwvxyz";
        for (String letter: letters.split("")) {

            String url = flaskBaseUrl + "/scrape-players/" + letter;
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Scraping triggered successfully in Flask.");
                } else {
                    throw new RuntimeException("Failed to trigger scraping in Flask");
                }
            } catch (Exception e) {
                throw new RuntimeException("Error during scraping: " + e.getMessage());
            }
        }
    }

    public void scrapePlayersFromNbaCom() {

        String url = flaskBaseUrl + "/scrape_players_nba_com";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Scraping triggered successfully in Flask.");
            } else {
                throw new RuntimeException("Failed to trigger scraping in Flask. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // Obsługa wyjątków, gdy coś poszło nie tak
            throw new RuntimeException("Error during scraping players: " + e.getMessage());
        }
    }

}
