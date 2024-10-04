package com.example.backend.player.mapper;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.entity.Player;
import org.mapstruct.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
@Mapper
public interface PlayerMapper {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM d, yyyy");

    Player playerDtoToPlayer(PlayerDTO dto);

    public PlayerDTO playerToPlayerDto(Player player);

}
