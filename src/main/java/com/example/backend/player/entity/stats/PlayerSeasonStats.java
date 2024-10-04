package com.example.backend.player.entity.stats;

import com.example.backend.player.entity.Player;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "player_season_stats")
public class PlayerSeasonStats extends PlayerStats{

    @Column(nullable = false)
    private String season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;


}
