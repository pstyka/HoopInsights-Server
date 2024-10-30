package com.example.backend.player.controller;

import com.example.backend.player.dto.PlayerDTO;
import com.example.backend.player.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/players")
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/get-players")
    public Page<PlayerDTO> getPlayers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String height,
            @RequestParam(required = false) String weight,
            @RequestParam(required = false) String teamName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return playerService.getAllPlayers(firstName, lastName, position, height, weight, teamName, page, size);
    }


    @GetMapping("/get-player/{id}")
    public PlayerDTO getPlayerById(@PathVariable Long id) {
        log.debug("Get player by id: {}", id);
        return playerService.getPlayerById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

}
