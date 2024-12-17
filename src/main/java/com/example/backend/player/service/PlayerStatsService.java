package com.example.backend.player.service;

import com.example.backend.player.dto.BestPlayerForAnalysisDTO;
import com.example.backend.player.dto.PlayerSeasonStatsDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.entity.stats.PlayerSeasonStats;
import com.example.backend.player.mapper.PlayerSeasonStatsMapper;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.player.repository.stats.PlayerSeasonStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerStatsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerSeasonStatsRepository playerSeasonStatsRepository;

    @Autowired
    private PlayerSeasonStatsMapper playerSeasonStatsMapper;

    public PlayerStatsService(PlayerSeasonStatsRepository playerSeasonStatsRepository, PlayerSeasonStatsMapper playerSeasonStatsMapper) {
        this.playerSeasonStatsRepository = playerSeasonStatsRepository;
        this.playerSeasonStatsMapper = playerSeasonStatsMapper;
    }

    public List<PlayerSeasonStatsDTO> getPlayerSeasonStatsByPlayerId(Long playerId) {
        List<PlayerSeasonStats> seasonStats = playerSeasonStatsRepository.findByPlayerId(playerId);
        return playerSeasonStatsMapper.playerSeasonStatsToPlayerSeasonStatsDTOs(seasonStats);
    }

    public List<BestPlayerForAnalysisDTO> getAllPlayerStatsForCurrentSeason() {
        return playerRepository.findAll().stream()
                .map(player -> {
                    PlayerSeasonStatsDTO statsDTO = playerSeasonStatsRepository
                            .findByPlayerAndSeason(player, "2024-25")
                            .stream()
                            .findFirst()
                            .map(stat -> new PlayerSeasonStatsDTO(
                                    "2024-25",
                                    stat.getGamesPlayed(),
                                    stat.getGamesStarted(),
                                    stat.getMinutesPlayed(),
                                    stat.getFieldGoals(),
                                    stat.getFieldGoalsAttempts(),
                                    stat.getFieldGoalsPercent(),
                                    stat.getThreePointersMade(),
                                    stat.getThreePointersAttempts(),
                                    stat.getThreePointersPercent(),
                                    stat.getTwoPointersAttempts(),
                                    stat.getTwoPointersPercent(),
                                    stat.getFreeThrowAttempts(),
                                    stat.getFreeThrowPercent(),
                                    stat.getOffensiveRebounds(),
                                    stat.getDefensiveRebounds(),
                                    stat.getRebounds(),
                                    stat.getAssists(),
                                    stat.getSteals(),
                                    stat.getBlocks(),
                                    stat.getTurnovers(),
                                    stat.getFouls(),
                                    stat.getPoints()
                            )).orElse(null);
                    return new BestPlayerForAnalysisDTO(
                            player.getFirstName(),
                            player.getLastName(),
                            player.getTeam().getName(),
                            statsDTO
                    );
                })
                .collect(Collectors.toList());
    }
}
