package com.example.backend.messageReceiver.listener;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.mapper.PlayerMapper;
import com.example.backend.player.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayerDataReceiver {

    @Autowired
    private PlayerRepository playerRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "player_queue")
    public void receiveMessage(String message) {
        try {
            Map<String, Object> playerData = objectMapper.readValue(message, Map.class);

            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setFirstName((String) playerData.get("firstName"));
            playerDTO.setLastName((String) playerData.get("lastName"));
            playerDTO.setPosition((String) playerData.get("position"));
            playerDTO.setHand((String) playerData.get("hand"));
            playerDTO.setBirthDate((String) playerData.get("birthDate"));
            playerDTO.setHeightInch((String) playerData.get("heightInch"));
            playerDTO.setHeightCm((String) playerData.get("heightCm"));
            playerDTO.setWeightLbs((String) playerData.get("weightLbs"));
            playerDTO.setWeightKg((String) playerData.get("weightKg"));
            playerDTO.setIsActive(Boolean.valueOf((String) playerData.get("isActive")));


            Player player = PlayerMapper.playerDtoToPlayer(playerDTO);
            playerRepository.save(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
