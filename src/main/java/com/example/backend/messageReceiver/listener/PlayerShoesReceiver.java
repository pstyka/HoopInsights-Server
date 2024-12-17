package com.example.backend.messageReceiver.listener;

import com.example.backend.player.entity.Player;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.shoe.dto.PlayerShoeDataDTO;
import com.example.backend.shoe.entity.Shoe;
import com.example.backend.shoe.repository.ShoeRepository;
import com.example.backend.team.entity.Team;
import com.example.backend.team.repository.TeamRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlayerShoesReceiver {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ShoeRepository shoeRepository;

    @RabbitListener(queues = "playerShoeQueue")
    @Transactional
    public void receivePlayerShoeData(PlayerShoeDataDTO playerShoeDTO) {
        System.out.println("Odebrano dane zawodnika: " + playerShoeDTO);

        // Znalezienie lub utworzenie gracza
        Optional<Player> playerOpt = playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(
                playerShoeDTO.getFirstName(), playerShoeDTO.getLastName());

        Player player = playerOpt.orElseGet(() -> {
            System.out.println("Tworzenie nowego zawodnika: " + playerShoeDTO.getFirstName() + " " + playerShoeDTO.getLastName());
            Player newPlayer = new Player();
            newPlayer.setFirstName(playerShoeDTO.getFirstName());
            newPlayer.setLastName(playerShoeDTO.getLastName());
            return playerRepository.save(newPlayer);
        });

        // Ustawienie drużyny zawodnika, jeśli jest dostępna
        String teamName = playerShoeDTO.getTeamName();
        if (teamName != null) {
            Team team = teamRepository.findByName(teamName)
                    .orElseGet(() -> {
                        System.out.println("Tworzenie nowej drużyny: " + teamName);
                        Team newTeam = new Team();
                        newTeam.setName(teamName);
                        return teamRepository.save(newTeam);
                    });

            if (player.getTeam() == null || !player.getTeam().equals(team)) {
                player.setTeam(team);
            }
        }

        // Aktualizacja zdjęcia zawodnika
        if (playerShoeDTO.getImageUrl() != null &&
                !playerShoeDTO.getImageUrl().equals(player.getPhotoUrl())) {
            player.setPhotoUrl(playerShoeDTO.getImageUrl());
        }

        // Znalezienie lub utworzenie obecnego buta zawodnika
        String currentShoeBrand = playerShoeDTO.getShoeBrand();
        String currentShoeModel = playerShoeDTO.getShoeModel();
        Shoe currentShoe = shoeRepository.findByBrandAndModel(currentShoeBrand, currentShoeModel)
                .orElseGet(() -> {
                    Shoe newShoe = new Shoe();
                    newShoe.setBrand(currentShoeBrand);
                    newShoe.setModel(currentShoeModel);
                    return shoeRepository.save(newShoe);
                });

        player.setCurrentShoe(currentShoe);

        // Zapisanie danych zawodnika
        playerRepository.save(player);

        System.out.println("Dane zawodnika zaktualizowane: " + player);
    }
}
