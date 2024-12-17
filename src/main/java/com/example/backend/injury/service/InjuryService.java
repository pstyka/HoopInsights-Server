package com.example.backend.injury.service;

import com.example.backend.injury.entity.Injury;
import com.example.backend.injury.repository.InjuryRepository;
import com.example.backend.player.dto.InjuryAnalysisDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.shoe.entity.Shoe;
import com.example.backend.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InjuryService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private InjuryRepository injuryRepository;

    public List<InjuryAnalysisDTO> getPlayerAnalysisData() {
        List<Player> players = playerRepository.findAll();

        return players.stream()
                .filter(player -> player.getInjuries() != null && !player.getInjuries().isEmpty())
                .map(player -> {

            Team team = player.getTeam();
            String teamName = team != null ? team.getName() : "Unknown Team";


            Shoe currentShoe = player.getCurrentShoe();
            String shoeBrand = currentShoe != null ? currentShoe.getBrand() : "Unknown Brand";
            String shoeModel = currentShoe != null ? currentShoe.getModel() : "Unknown Model";
            String shoeType = currentShoe != null ? currentShoe.getType() : null;

                    Injury latestInjury = player.getInjuries().stream()
                            .max(Comparator.comparing(Injury::getUpdated))
                            .orElse(null);

                    if (latestInjury == null) {
                        return null;
                    }

                    String position = latestInjury.getPosition();
                    LocalDate updated = latestInjury.getUpdated();
                    String injury = latestInjury.getInjury();
                    String injuryStatus = latestInjury.getInjuryStatus();

            return  InjuryAnalysisDTO.builder()
                    .firstName(player.getFirstName())
                    .lastName(player.getLastName())
                    .birthDate(player.getBirthDate())
                    .heightInch(player.getHeightInch())
                    .heightCm(player.getHeightCm())
                    .weightLbs(player.getWeightLbs())
                    .weightKg(player.getWeightKg())
                    .teamName(teamName)
                    .shoeBrand(shoeBrand)
                    .shoeModel(shoeModel)
                    .shoeType(shoeType)
                    .position(position)
                    .updated(updated)
                    .injury(injury)
                    .injuryStatus(injuryStatus)
                    .build();
        }).collect(Collectors.toList());
    }

    public void clearInjuries() {
        injuryRepository.deleteAll();
        System.out.println("All injuries cleared from the database.");
    }
}
