package com.example.backend.player.mapper;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public class PlayerMapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM d, yyyy");

    public static Player playerDtoToPlayer(PlayerDTO dto) throws ParseException {
        Player player = new Player();
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setPosition(dto.getPosition());
        player.setHand(dto.getHand());
        player.setBirthDate(dto.getBirthDate());
        player.setHeightInch(dto.getHeightInch());
        player.setHeightCm(dto.getHeightCm());
        player.setWeightLbs(dto.getWeightLbs());
        player.setIsActive(dto.getIsActive());
        return player;
    }

}
