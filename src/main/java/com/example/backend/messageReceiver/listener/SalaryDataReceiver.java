package com.example.backend.messageReceiver.listener;

import com.example.backend.player.dto.SalaryDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.entity.salary.Salary;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.player.repository.salary.SalaryRepository;
import com.example.backend.team.entity.Team;
import com.example.backend.team.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SalaryDataReceiver {

    private PlayerRepository playerRepository;

    private TeamRepository teamRepository;

    private SalaryRepository salaryRepository;

    @RabbitListener(queues = "salaryQueue")
    @Transactional
    public void receiveSalaryData(SalaryDTO salaryDTO) {
        System.out.println("Received salary data: " + salaryDTO);

        Optional<Player> playerOpt = playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(
                salaryDTO.getFirstName(), salaryDTO.getLastName());
        Player player = playerOpt.orElseGet(() -> {
            System.out.println("Creating new player: " + salaryDTO.getFirstName() + " " + salaryDTO.getLastName());
            Player newPlayer = new Player();
            newPlayer.setFirstName(salaryDTO.getFirstName());
            newPlayer.setLastName(salaryDTO.getLastName());
            return playerRepository.save(newPlayer);
        });

        Optional<Team> teamOpt = teamRepository.findByAbb(salaryDTO.getTeam());
        if (teamOpt.isPresent()) {
            Salary salary = Salary.builder()
                    .salary(salaryDTO.getSalary())
                    .team(teamOpt.get())
                    .player(player)
                    .build();
            salaryRepository.save(salary);
        }

        System.out.println("Saved salary data for player: " + player.getFirstName() + " " + player.getLastName());
    }
}
