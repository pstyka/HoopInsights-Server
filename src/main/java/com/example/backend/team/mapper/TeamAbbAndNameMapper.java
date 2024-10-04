package com.example.backend.team.mapper;

import com.example.backend.team.dto.TeamAbbAndNameDTO;
import com.example.backend.team.entity.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamAbbAndNameMapper {

    Team teamDtoToTeam(TeamAbbAndNameDTO teamAbbAndNameDTO);

    TeamAbbAndNameDTO teamToTeamDto(Team team);
}
