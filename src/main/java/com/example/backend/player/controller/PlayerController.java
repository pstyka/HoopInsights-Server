package com.example.backend.player.controller;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/players")
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/get-players")
    public Page<PlayerDTO> getPlayers(@RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String position) {
        log.debug("Get players by first name: {}, last name: {}, position: {}", firstName, lastName, position);
        return playerService.getAllPlayers(firstName, lastName, position);
    }

    @GetMapping("/get-player/{id}")
    public PlayerDTO getPlayerById(@RequestParam Long id) {
        log.debug("Get player by id: {}", id);
        return playerService.getPlayerById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

}
