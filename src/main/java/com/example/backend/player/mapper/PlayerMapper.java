package com.example.backend.player.mapper;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
@Mapper
public interface PlayerMapper {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM d, yyyy");

    Player playerDtoToPlayer(PlayerDTO dto);

    @Mapping(source = "team.logoUrl", target = "teamLogoUrl")
    @Mapping(source = "team.name", target = "teamName")

    public PlayerDTO playerToPlayerDto(Player player);

}
