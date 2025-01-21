package com.example.backend.player.repository.stats;

import com.example.backend.player.entity.Player;
import com.example.backend.player.entity.stats.PlayerSeasonStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerSeasonStatsRepository extends JpaRepository<PlayerSeasonStats, Long> {
    List<PlayerSeasonStats> findByPlayerId(Long playerId);
    Optional<PlayerSeasonStats> findByPlayerAndSeason(Player player, String season);
}
