package com.example.backend.player.service;

import com.example.backend.player.entity.Player;
import com.example.backend.player.entity.stats.PlayerSeasonStats;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.player.repository.stats.PlayerSeasonStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerScrapingService {

    private final RestTemplate restTemplate;
    private final PlayerRepository playerRepository;
    private final PlayerSeasonStatsRepository playerSeasonStatsRepository;

    @Value("${flask.base-url}")
    private String flaskBaseUrl;

    public void scrapeAndSavePlayers(String letter, String season) {
        String url = flaskBaseUrl + "/scrape-players-stats/" + season + "/" + letter;

        try {
            ResponseEntity<List<Player>> response = restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<List<Player>>() {});

            List<Player> players = response.getBody();

            if (players != null) {
                for (Player player : players) {
                    Optional<Player> existingPlayer = playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(player.getFirstName(), player.getLastName());

                    Player savedPlayer;
                    if (existingPlayer.isPresent()) {
                        savedPlayer = existingPlayer.get();
                        updatePlayerData(savedPlayer, player);
                    } else {
                        savedPlayer = playerRepository.save(player);
                    }

                    savePlayerSeasonStats(savedPlayer, player.getPlayerSeasonStats());
                }
            } else {
                throw new RuntimeException("Brak danych od Flask!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas scrapowania: " + e.getMessage());
        }
    }

    private void updatePlayerData(Player existingPlayer, Player newPlayerData) {
        existingPlayer.setHeightCm(newPlayerData.getHeightCm());
        existingPlayer.setHeightInch(newPlayerData.getHeightInch());
        existingPlayer.setWeightKg(newPlayerData.getWeightKg());
        existingPlayer.setWeightLbs(newPlayerData.getWeightLbs());
        existingPlayer.setPosition(newPlayerData.getPosition());
        existingPlayer.setIsActive(newPlayerData.getIsActive());
        existingPlayer.setJerseyNumber(newPlayerData.getJerseyNumber());

        playerRepository.save(existingPlayer);
    }

    private void savePlayerSeasonStats(Player player, List<PlayerSeasonStats> seasonStatsList) {
        for (PlayerSeasonStats seasonStats : seasonStatsList) {
            PlayerSeasonStats newSeasonStats = PlayerSeasonStats.builder()
                    .season(seasonStats.getSeason())
                    .gamesPlayed(seasonStats.getGamesPlayed())
                    .gamesStarted(seasonStats.getGamesStarted())
                    .minutesPlayed(seasonStats.getMinutesPlayed())
                    .fieldGoals(seasonStats.getFieldGoals())
                    .fieldGoalsAttempts(seasonStats.getFieldGoalsAttempts())
                    .fieldGoalsPercent(seasonStats.getFieldGoalsPercent())
                    .threePointersMade(seasonStats.getThreePointersMade())
                    .threePointersAttempts(seasonStats.getThreePointersAttempts())
                    .threePointersPercent(seasonStats.getThreePointersPercent())
                    .twoPointersAttempts(seasonStats.getTwoPointersAttempts())
                    .twoPointersPercent(seasonStats.getTwoPointersPercent())
                    .freeThrowAttempts(seasonStats.getFreeThrowAttempts())
                    .freeThrowPercent(seasonStats.getFreeThrowPercent())
                    .offensiveRebounds(seasonStats.getOffensiveRebounds())
                    .defensiveRebounds(seasonStats.getDefensiveRebounds())
                    .rebounds(seasonStats.getRebounds())
                    .assists(seasonStats.getAssists())
                    .steals(seasonStats.getSteals())
                    .blocks(seasonStats.getBlocks())
                    .turnovers(seasonStats.getTurnovers())
                    .fouls(seasonStats.getFouls())
                    .points(seasonStats.getPoints())
                    .player(player)
                    .build();

            playerSeasonStatsRepository.save(newSeasonStats);
        }
    }



}
