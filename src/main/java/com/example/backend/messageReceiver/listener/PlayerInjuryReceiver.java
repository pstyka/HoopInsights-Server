package com.example.backend.messageReceiver.listener;

import com.example.backend.injury.dto.PlayerInjuryDTO;
import com.example.backend.injury.entity.Injury;
import com.example.backend.injury.repository.InjuryRepository;
import com.example.backend.player.entity.Player;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.team.entity.Team;
import com.example.backend.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PlayerInjuryReceiver {

    @Autowired
    private InjuryRepository injuryRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @RabbitListener(queues = "playerInjuryQueue")
    @Transactional
    public void receiveInjuryData(PlayerInjuryDTO injuryData) {
        System.out.println("Injury report reveived: " + injuryData);

        Optional<Player> playerOpt = playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(
                injuryData.getFirstName(), injuryData.getLastName());

        Player player = playerOpt.orElseGet(() -> {
            Player newPlayer = new Player();
            newPlayer.setFirstName(injuryData.getFirstName());
            newPlayer.setLastName(injuryData.getLastName());
            return playerRepository.save(newPlayer);
        });

        String abb = injuryData.getAbb();
        Optional<Team> team = teamRepository.findByAbb(abb);


        if (player.getTeam() == null || !player.getTeam().equals(team)) {
            player.setTeam(team.get());
            playerRepository.save(player);
            System.out.println("Zaktualizowano drużynę gracza na: " + abb);
        }

        Injury injury = new Injury();
        injury.setPlayer(player);
        injury.setUpdated(injuryData.getUpdated());
        injury.setInjury(injuryData.getInjury());
        injury.setPosition(injuryData.getPosition());
        injury.setInjuryStatus(injuryData.getInjuryStatus());

        injuryRepository.save(injury);
        System.out.println("Uraz zapisany w bazie: " + injury);
    }
}
