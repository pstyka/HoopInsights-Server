package com.example.backend.player.service;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.mapper.PlayerMapper;
import com.example.backend.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerMapper playerMapper;

    public Page<PlayerDTO> getAllPlayers(String firstName, String lastName, String position) {

        PageRequest pageRequest = PageRequest.of(0, 25);

        Page<Player> players;

        if(StringUtils.hasText(firstName) && lastName == null && position == null) {
            players = findPlayersByFirstName(firstName, pageRequest);
        } else if(firstName == null && StringUtils.hasText(lastName) && position == null) {
            players = findPlayersByLastName(lastName, pageRequest);
        } else if(firstName == null && lastName == null && StringUtils.hasText(position)) {
            players = findPlayersByPosition(position, pageRequest);
        } else if(StringUtils.hasText(firstName) && StringUtils.hasText(lastName) && position == null) {
            players = findPlayersByFirstNameAndLastName(firstName, lastName, pageRequest);
        } else if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName) && StringUtils.hasText(position)) {
            players = findPlayersByFirstNameAndLastNameAndPosition(firstName, lastName, position, pageRequest);
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

}
