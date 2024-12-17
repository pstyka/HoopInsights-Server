package com.example.backend.team.mapper;

import com.example.backend.team.dto.TeamDTO;
import com.example.backend.team.entity.Team;
import org.mapstruct.Mapper;

@Mapper
public interface TeamMapper {

    Team teamDtoToTeam(TeamDTO teamDTO);

    TeamDTO teamToTeamDto(Team team);
}
