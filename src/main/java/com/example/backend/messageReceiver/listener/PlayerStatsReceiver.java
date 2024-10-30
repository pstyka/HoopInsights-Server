package com.example.backend.messageReceiver.listener;

import com.example.backend.player.entity.Player;
import com.example.backend.player.entity.stats.PlayerSeasonStats;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.player.repository.stats.PlayerSeasonStatsRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerStatsReceiver {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerSeasonStatsRepository playerSeasonStatsRepository;

//    @RabbitListener(queues = "player_queue")
//    public void receiveMessage(Map<String, Object> playerData) {
//        try {
//
//            String firstName = (String) playerData.get("firstName");
//            String lastName = (String) playerData.get("lastName");
//
//            Optional<Player> playerOptional = playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(firstName, lastName);
//
//            if (playerOptional.isPresent()) {
//                Player player = playerOptional.get();
//
//
//                List<Map<String, Object>> seasonStats = (List<Map<String, Object>>) playerData.get("seasonStats");
//                for (Map<String, Object> stats : seasonStats) {
//                    PlayerSeasonStats playerSeasonStats = new PlayerSeasonStats();
//                    playerSeasonStats.setSeason((String) stats.get("season"));
//
//                    String gamesPlayedStr = (String) stats.get("games_played");
//                    if (gamesPlayedStr != null && !gamesPlayedStr.isEmpty()) {
//                        playerSeasonStats.setGamesPlayed(Integer.parseInt(gamesPlayedStr));
//                    }
//
//                    String gamesStartedStr = (String) stats.get("games_started");
//                    if (gamesStartedStr != null && !gamesStartedStr.isEmpty()) {
//                        playerSeasonStats.setGamesStarted(Integer.parseInt(gamesStartedStr));
//                    }
//
//                    String minutesPlayedStr = (String) stats.get("minutes_per_game");
//                    if (minutesPlayedStr != null && !minutesPlayedStr.isEmpty()) {
//                        playerSeasonStats.setMinutesPlayed(Double.parseDouble(minutesPlayedStr));
//                    }
//
//                    String fieldGoalsStr = (String) stats.get("field_goals_per_game");
//                    if (fieldGoalsStr != null && !fieldGoalsStr.isEmpty()) {
//                        playerSeasonStats.setFieldGoals(Double.parseDouble(fieldGoalsStr));
//                    }
//
//                    String fieldGoalAttemptsStr = (String) stats.get("field_goal_attempts_per_game");
//                    if (fieldGoalAttemptsStr != null && !fieldGoalAttemptsStr.isEmpty()) {
//                        playerSeasonStats.setFieldGoalsAttempts(Double.parseDouble(fieldGoalAttemptsStr));
//                    }
//
//                    String fieldGoalsPercentStr = (String) stats.get("field_goal_percentage");
//                    if (fieldGoalsPercentStr != null && !fieldGoalsPercentStr.isEmpty()) {
//                        playerSeasonStats.setFieldGoalsPercent(Double.parseDouble(fieldGoalsPercentStr));
//                    }
//
//                    String threePointersMadeStr = (String) stats.get("three_pointers_per_game");
//                    if (threePointersMadeStr != null && !threePointersMadeStr.isEmpty()) {
//                        playerSeasonStats.setThreePointersMade(Double.parseDouble(threePointersMadeStr));
//                    }
//
//                    String threePointerAttemptsStr = (String) stats.get("three_pointer_attempts_per_game");
//                    if (threePointerAttemptsStr != null && !threePointerAttemptsStr.isEmpty()) {
//                        playerSeasonStats.setThreePointersAttempts(Double.parseDouble(threePointerAttemptsStr));
//                    }
//
//                    String threePointerPercentStr = (String) stats.get("three_pointer_percentage");
//                    if (threePointerPercentStr != null && !threePointerPercentStr.isEmpty()) {
//                        playerSeasonStats.setThreePointersPercent(Double.parseDouble(threePointerPercentStr));
//                    }
//
//                    String twoPointersAttemptsStr = (String) stats.get("two_pointer_attempts_per_game");
//                    if (twoPointersAttemptsStr != null && !twoPointersAttemptsStr.isEmpty()) {
//                        playerSeasonStats.setTwoPointersAttempts(Double.parseDouble(twoPointersAttemptsStr));
//                    }
//
//                    String twoPointerPercentStr = (String) stats.get("two_pointer_percentage");
//                    if (twoPointerPercentStr != null && !twoPointerPercentStr.isEmpty()) {
//                        playerSeasonStats.setTwoPointersPercent(Double.parseDouble(twoPointerPercentStr));
//                    }
//
//                    String freeThrowAttemptsStr = (String) stats.get("free_throw_attempts_per_game");
//                    if (freeThrowAttemptsStr != null && !freeThrowAttemptsStr.isEmpty()) {
//                        playerSeasonStats.setFreeThrowAttempts(Double.parseDouble(freeThrowAttemptsStr));
//                    }
//
//                    String freeThrowPercentStr = (String) stats.get("free_throw_percentage");
//                    if (freeThrowPercentStr != null && !freeThrowPercentStr.isEmpty()) {
//                        playerSeasonStats.setFreeThrowPercent(Double.parseDouble(freeThrowPercentStr));
//                    }
//
//                    String offensiveReboundsStr = (String) stats.get("offensive_rebounds_per_game");
//                    if (offensiveReboundsStr != null && !offensiveReboundsStr.isEmpty()) {
//                        playerSeasonStats.setOffensiveRebounds(Double.parseDouble(offensiveReboundsStr));
//                    }
//
//                    String defensiveReboundsStr = (String) stats.get("defensive_rebounds_per_game");
//                    if (defensiveReboundsStr != null && !defensiveReboundsStr.isEmpty()) {
//                        playerSeasonStats.setDefensiveRebounds(Double.parseDouble(defensiveReboundsStr));
//                    }
//
//                    String reboundsStr = (String) stats.get("total_rebounds_per_game");
//                    if (reboundsStr != null && !reboundsStr.isEmpty()) {
//                        playerSeasonStats.setRebounds(Double.parseDouble(reboundsStr));
//                    }
//
//                    String assistsStr = (String) stats.get("assists_per_game");
//                    if (assistsStr != null && !assistsStr.isEmpty()) {
//                        playerSeasonStats.setAssists(Double.parseDouble(assistsStr));
//                    }
//
//                    String stealsStr = (String) stats.get("steals_per_game");
//                    if (stealsStr != null && !stealsStr.isEmpty()) {
//                        playerSeasonStats.setSteals(Double.parseDouble(stealsStr));
//                    }
//
//                    String blocksStr = (String) stats.get("blocks_per_game");
//                    if (blocksStr != null && !blocksStr.isEmpty()) {
//                        playerSeasonStats.setBlocks(Double.parseDouble(blocksStr));
//                    }
//
//                    String turnoversStr = (String) stats.get("turnovers_per_game");
//                    if (turnoversStr != null && !turnoversStr.isEmpty()) {
//                        playerSeasonStats.setTurnovers(Double.parseDouble(turnoversStr));
//                    }
//
//                    String foulsStr = (String) stats.get("personal_fouls_per_game");
//                    if (foulsStr != null && !foulsStr.isEmpty()) {
//                        playerSeasonStats.setFouls(Double.parseDouble(foulsStr));
//                    }
//
//                    String pointsStr = (String) stats.get("points_per_game");
//                    if (pointsStr != null && !pointsStr.isEmpty()) {
//                        playerSeasonStats.setPoints(Double.parseDouble(pointsStr));
//                    }
//
//
//                    playerSeasonStats.setPlayer(player);
//                    playerSeasonStatsRepository.save(playerSeasonStats);
//                }
//
//                System.out.println("Zapisano dane zawodnika: " + player.getFirstName() + " " + player.getLastName());
//
//            } else {
//                System.out.println("Zawodnik " + firstName + " " + lastName + " nie został znaleziony w bazie.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
