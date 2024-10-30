package com.example.backend.player.mapper;

import com.example.backend.player.dto.PlayerSeasonStatsDTO;
import com.example.backend.player.entity.stats.PlayerSeasonStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlayerSeasonStatsMapper {

    PlayerSeasonStatsMapper INSTANCE = Mappers.getMapper(PlayerSeasonStatsMapper.class);

    @Mapping(source = "season", target = "season")
    PlayerSeasonStatsDTO playerSeasonStatsToPlayerSeasonStatsDTO(PlayerSeasonStats playerSeasonStats);

    List<PlayerSeasonStatsDTO> playerSeasonStatsToPlayerSeasonStatsDTOs(List<PlayerSeasonStats> playerSeasonStats);
}