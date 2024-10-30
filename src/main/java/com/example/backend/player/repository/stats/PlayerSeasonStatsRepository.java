package com.example.backend.player.repository.stats;

import com.example.backend.player.entity.stats.PlayerSeasonStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerSeasonStatsRepository extends JpaRepository<PlayerSeasonStats, Long> {
    List<PlayerSeasonStats> findByPlayerId(Long playerId);
}
