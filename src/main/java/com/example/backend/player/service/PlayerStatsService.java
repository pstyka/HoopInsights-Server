package com.example.backend.player.service;

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

@Service
public class PlayerStatsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerSeasonStatsRepository playerSeasonStatsRepository;

    @Autowired
    private PlayerSeasonStatsMapper playerSeasonStatsMapper;

    public void savePlayerSeasonStats(String firstName, String lastName, PlayerSeasonStats seasonStats) {
        Optional<Player> playerOptional = playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(firstName, lastName);

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            seasonStats.setPlayer(player);

            playerSeasonStatsRepository.save(seasonStats);

            System.out.println("Zapisano statystyki sezonowe dla zawodnika: " + player.getFirstName() + " " + player.getLastName());
        } else {
            System.out.println("Zawodnik " + firstName + " " + lastName + " nie został znaleziony.");
        }
    }

    public PlayerStatsService(PlayerSeasonStatsRepository playerSeasonStatsRepository, PlayerSeasonStatsMapper playerSeasonStatsMapper) {
        this.playerSeasonStatsRepository = playerSeasonStatsRepository;
        this.playerSeasonStatsMapper = playerSeasonStatsMapper;
    }

    public List<PlayerSeasonStatsDTO> getPlayerSeasonStatsByPlayerId(Long playerId) {
        List<PlayerSeasonStats> seasonStats = playerSeasonStatsRepository.findByPlayerId(playerId);
        return playerSeasonStatsMapper.playerSeasonStatsToPlayerSeasonStatsDTOs(seasonStats);
    }
}
