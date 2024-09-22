package com.example.backend.team.mapper;

import com.example.backend.team.dto.TeamApiResponseDTO;
import com.example.backend.team.entity.Team;
import org.mapstruct.Mapper;

@Mapper
public interface TeamMapper {

    Team teamDtoToTeam(TeamApiResponseDTO.TeamDTO teamAbbAndNameDTO);

    TeamApiResponseDTO.TeamDTO teamToTeamDto(Team team);
}
