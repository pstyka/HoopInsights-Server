package com.example.backend.messageReceiver.listener;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PlayerDataReceiver {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "player_queue")
    public void receiveMessage(@Payload PlayerDTO playerDTO) {
        try {
            log.info("Message received: " + playerDTO);

            Optional<Player> existingPlayer = playerRepository
                    .findByFirstNameAndLastNameIsLikeIgnoreCase(playerDTO.getFirstName(), playerDTO.getLastName());

            if (existingPlayer.isPresent()) {
                log.info("Aktualizacja zawodnika: " + playerDTO.getFirstName() + " " + playerDTO.getLastName());
                Player player = existingPlayer.get();
                updatePlayer(player, playerDTO);
                playerRepository.save(player);
            } else {
                log.info("Tworzenie nowego zawodnika: " + playerDTO.getFirstName() + " " + playerDTO.getLastName());
                Player newPlayer = new Player();
                updatePlayer(newPlayer, playerDTO);
                playerRepository.save(newPlayer);
            }
        } catch (Exception e) {
            log.error("Błąd podczas przetwarzania wiadomości: ", e);
        }
    }


    private void updatePlayer(Player player, PlayerDTO playerDTO) {
        if (playerDTO.getFirstName() != null && !playerDTO.getFirstName().isEmpty()) {
            player.setFirstName(playerDTO.getFirstName());
        }
        if (playerDTO.getLastName() != null && !playerDTO.getLastName().isEmpty()) {
            player.setLastName(playerDTO.getLastName());
        }
        if (playerDTO.getHeightInch() != null && !playerDTO.getHeightInch().isEmpty()) {
            player.setHeightInch(playerDTO.getHeightInch());
        }
        if (playerDTO.getHeightCm() != null && !playerDTO.getHeightCm().isEmpty()) {
            player.setHeightCm(playerDTO.getHeightCm());
        }
        if (playerDTO.getWeightLbs() != null && !playerDTO.getWeightLbs().isEmpty()) {
            player.setWeightLbs(playerDTO.getWeightLbs());
        }
        if (playerDTO.getWeightKg() != null && !playerDTO.getWeightKg().isEmpty()) {
            player.setWeightKg(playerDTO.getWeightKg());
        }
        if (playerDTO.getCountry() != null && !playerDTO.getCountry().isEmpty()) {
            player.setCountry(playerDTO.getCountry());
        }
        if (playerDTO.getBirthDate() != null) {
            player.setBirthDate(playerDTO.getBirthDate());
        }
        if (playerDTO.getDraft() != null && !playerDTO.getDraft().isEmpty()) {
            player.setDraft(playerDTO.getDraft());
        }
        if (playerDTO.getPhotoUrl() != null && !playerDTO.getPhotoUrl().isEmpty()) {
            log.info("Setting photoUrl: " + playerDTO.getPhotoUrl());
            player.setPhotoUrl(playerDTO.getPhotoUrl());
        }
        if (playerDTO.getJerseyNumber() != null && !playerDTO.getJerseyNumber().isEmpty()) {
            player.setJerseyNumber(playerDTO.getJerseyNumber());
        }


        if (player.getTeam() != null) {
            player.setTeam(player.getTeam());
        }
        if (player.getPlayerSeasonStats() != null) {
            player.setPlayerSeasonStats(player.getPlayerSeasonStats());
        }
        if (player.getPlayerCareerStats() != null) {
            player.setPlayerCareerStats(player.getPlayerCareerStats());
        }
        if (player.getIsActive() != null) {
            player.setIsActive(player.getIsActive());
        }
        if (player.getHand() != null) {
            player.setHand(player.getHand());
        }
        if (player.getPosition() != null) {
            player.setPosition(player.getPosition());
        }
        if (player.getApiId() != null) {
            player.setApiId(player.getApiId());
        }
        if (player.getId() != null) {
            player.setId(player.getId());
        }
    }

}
