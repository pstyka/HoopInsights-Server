package com.example.backend.player.entity.stats;

import com.example.backend.player.entity.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "player_career_stats")
public class PlayerCareerStats extends PlayerStats{

    private int totalPoints;
    private int totalRebounds;
    private int totalAssists;
    private int totalBlocks;
    private int totalSteals;
    private int totalFouls;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}
