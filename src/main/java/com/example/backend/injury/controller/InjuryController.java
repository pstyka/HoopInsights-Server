package com.example.backend.injury.controller;

import com.example.backend.injury.service.InjuryService;
import com.example.backend.player.dto.InjuryAnalysisDTO;
import com.example.backend.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/injury")

public class InjuryController {

    @Autowired
    private InjuryService injuryService;

    @GetMapping("/injuries")
    public List<InjuryAnalysisDTO> getPlayerAnalysis() {
        return injuryService.getPlayerAnalysisData();
    }
}
