package com.example.backend.player.repository.stats;

import com.example.backend.player.entity.stats.PlayerCareerStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerCareerStatsRepository extends JpaRepository<PlayerCareerStats, Long> {
}
