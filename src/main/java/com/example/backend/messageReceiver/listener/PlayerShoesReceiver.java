package com.example.backend.messageReceiver.listener;

import com.example.backend.player.entity.Player;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.shoe.dto.PlayerShoeDataDTO;
import com.example.backend.shoe.dto.ShoeHistoryDTO;
import com.example.backend.shoe.entity.Shoe;
import com.example.backend.shoe.entity.ShoeHistory;
import com.example.backend.shoe.repository.ShoeHistoryRepository;
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
    private ShoeHistoryRepository shoeHistoryRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ShoeRepository shoeRepository;

    @RabbitListener(queues = "playerShoeQueue")
    @Transactional
    public void receivePlayerShoeData(PlayerShoeDataDTO playerShoeData) {
        System.out.println("Odebrano dane zawodnika: " + playerShoeData);

        Optional<Player> playerOpt = playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(
                playerShoeData.getFirstName(), playerShoeData.getLastName());

        Player player = playerOpt.orElseGet(() -> {
            System.out.println("Tworzenie nowego zawodnika: " + playerShoeData.getFirstName() + " " + playerShoeData.getLastName());
            Player newPlayer = new Player();
            newPlayer.setFirstName(playerShoeData.getFirstName());
            newPlayer.setLastName(playerShoeData.getLastName());
            return playerRepository.save(newPlayer);
        });

        String teamName = playerShoeData.getTeamName();
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

        if (playerShoeData.getImageUrl() != null &&
                !playerShoeData.getImageUrl().equals(player.getPhotoUrl())) {
            player.setPhotoUrl(playerShoeData.getImageUrl());
        }
        // Ustawienie currentShoe
        String currentShoeBrand = playerShoeData.getCurrentShoeBrand();
        String currentShoeModel = playerShoeData.getCurrentShoeModel();
        Shoe currentShoe = shoeRepository.findByBrandAndModel(currentShoeBrand, currentShoeModel)
                .orElseGet(() -> {
                    Shoe newShoe = new Shoe();
                    newShoe.setBrand(currentShoeBrand);
                    newShoe.setModel(currentShoeModel);
                    return shoeRepository.save(newShoe);
                });
        player.setCurrentShoe(currentShoe);

        playerRepository.save(player);

        for (ShoeHistoryDTO shoeHistoryDTO : playerShoeData.getShoeHistory()) {
            Shoe shoe = shoeRepository.findByBrandAndModel(shoeHistoryDTO.getBrand(), shoeHistoryDTO.getModel())
                    .orElseGet(() -> {
                        Shoe newShoe = new Shoe();
                        newShoe.setBrand(shoeHistoryDTO.getBrand());
                        newShoe.setModel(shoeHistoryDTO.getModel());
                        return shoeRepository.save(newShoe);
                    });

            ShoeHistory shoeHistory = new ShoeHistory();
            shoeHistory.setPlayer(player);
            shoeHistory.setShoe(shoe);
            System.out.println("Ustawianie dateWorn: " + shoeHistoryDTO.getDateWorn());
            shoeHistory.setDateWorn(shoeHistoryDTO.getDateWorn());

            shoeHistoryRepository.save(shoeHistory);
        }
    }
}
