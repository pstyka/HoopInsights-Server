package com.example.backend.player.repository.stats;

import com.example.backend.player.entity.stats.PlayerSeasonStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerSeasonStatsRepository extends JpaRepository<PlayerSeasonStats, Long> {
}
