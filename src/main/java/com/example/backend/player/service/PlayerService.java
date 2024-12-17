package com.example.backend.player.service;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.dto.PlayerShoeDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.mapper.PlayerMapper;
import com.example.backend.player.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerMapper playerMapper;

    public Page<PlayerDTO> getAllPlayers(String firstName, String lastName, String position, String teamName, String heightInch, String weightLbs, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Player> players;

        if (StringUtils.hasText(teamName)) {
            players = findPlayersByTeamName(teamName, pageRequest);
        } else if (StringUtils.hasText(firstName) && !StringUtils.hasText(lastName) && !StringUtils.hasText(position)) {
            players = findPlayersByFirstName(firstName, pageRequest);
        } else if (!StringUtils.hasText(firstName) && StringUtils.hasText(lastName) && !StringUtils.hasText(position)) {
            players = findPlayersByLastName(lastName, pageRequest);
        } else if (!StringUtils.hasText(firstName) && !StringUtils.hasText(lastName) && StringUtils.hasText(position)) {
            players = findPlayersByPosition(position, pageRequest);
        } else if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName) && !StringUtils.hasText(position)) {
            players = findPlayersByFirstNameAndLastName(firstName, lastName, pageRequest);
        } else if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName) && StringUtils.hasText(position)) {
            players = findPlayersByFirstNameAndLastNameAndPosition(firstName, lastName, position, pageRequest);
        } else if (StringUtils.hasText(heightInch)) {
            players = findPlayersByHeightInch(heightInch, pageRequest);
        } else if (StringUtils.hasText(weightLbs)) {
            players = findPlayersByWeightLbs(weightLbs, pageRequest);
        } else {
            players = playerRepository.findAll(pageRequest);
        }

        return players.map(playerMapper::playerToPlayerDto);
    }

    public Optional<PlayerDTO> getPlayerById(Long id) {
        return playerRepository.findById(id).map(playerMapper::playerToPlayerDto);
    }

    private Page<Player> findPlayersByFirstName(String firstName, Pageable pageable) {
        return playerRepository.findByFirstNameIsLikeIgnoreCase(firstName, pageable);
    }

    private Page<Player> findPlayersByLastName(String lastName, Pageable pageable) {
        return playerRepository.findByLastNameIsLikeIgnoreCase(lastName, pageable);
    }

    private Page<Player> findPlayersByPosition(String position, Pageable pageable) {
        return playerRepository.findByPositionIsLikeIgnoreCase(position, pageable);
    }

    private Page<Player> findPlayersByFirstNameAndLastName(String firstName, String lastName, Pageable pageable) {
        return playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(firstName, lastName, pageable);
    }

    private Page<Player> findPlayersByFirstNameAndLastNameAndPosition(String firstName, String lastName, String position, Pageable pageable) {
        return playerRepository.findByFirstNameAndLastNameAndPositionIsLikeIgnoreCase(firstName, lastName, position, pageable);
    }

    private Page<Player> findPlayersByTeamName(String teamName, Pageable pageable) {
        return playerRepository.findPlayersByTeamName(teamName, pageable);
    }

    private Page<Player> findPlayersByHeightInch(String height, Pageable pageable) {
        return playerRepository.findPlayersByHeightInch(height, pageable);
    }

    private Page<Player> findPlayersByWeightLbs(String weight, Pageable pageable) {
        return playerRepository.findPlayersByWeightLbs(weight, pageable);
    }

    public List<PlayerShoeDTO> getPlayersWithAssignedShoes() {
        List<Player> players = playerRepository.findByCurrentShoeIsNotNull();

        return players.stream().map(player -> {
            PlayerShoeDTO dto = new PlayerShoeDTO();
            dto.setFirstName(player.getFirstName());
            dto.setLastName(player.getLastName());
            dto.setTeam(player.getTeam() != null ? player.getTeam().getName() : "Unknown Team");
            dto.setShoeType(player.getCurrentShoe() != null ? player.getCurrentShoe().getType() : "Unknown Type");
            return dto;
        }).collect(Collectors.toList());
    }
}
