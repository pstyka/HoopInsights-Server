package com.example.backend.player.service;

import com.example.backend.team.dto.TeamAbbAndNameDTO;
import com.example.backend.team.service.TeamsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class PlayerScrapingService {

    @Value("${flask.base-url}")
    private String flaskBaseUrl;

    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final TeamsService teamsService;

    private Logger logger;

    public PlayerScrapingService(RestTemplate restTemplate, RabbitTemplate rabbitTemplate, TeamsService teamsService) {
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.teamsService = teamsService;
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

   // @Async
    public void scrapeThisSeasonStats() {
        String letters = "abcdefghijklmnopqrstuwvxyz";
        for (String l: letters.split("")) {
            String url = flaskBaseUrl + "/scrape-this-season-stats/" + l;
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
        //logger.info("All scraping requests have been triggered.");
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
            throw new RuntimeException("Error during scraping players: " + e.getMessage());
        }
    }

    public void scrapeSalariesFromBasketballReferenceCom() throws InterruptedException {
        List<String> teamAbbs = teamsService.getTeamsAbbList().stream().map(TeamAbbAndNameDTO::getAbb).toList();
        for(String abb : teamAbbs) {
            String url = flaskBaseUrl + "/scrape-salaries/" + abb;
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Scraping triggered successfully in Flask.");
                } else {
                    throw new RuntimeException("Failed to trigger scraping in Flask. Status code: " + response.getStatusCode());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error during scraping salaries: " + e.getMessage());
            }
            Thread.sleep(7000);
        }
    }

    public String scrapeThisSeasonStatsForPlayers() {
        String flaskUrl = "http://flask-serivce:5000/scrape-this-season-stats-for-player";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, null, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error while scraping stats: " + e.getMessage(), e);
        }
    }

}
