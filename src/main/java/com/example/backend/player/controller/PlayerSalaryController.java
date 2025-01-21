package com.example.backend.player.controller;

import com.example.backend.player.dto.SalaryAnalysisDTO;
import com.example.backend.player.service.PlayerSalaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/players")
public class PlayerSalaryController {

    private final PlayerSalaryService salaryAnalysisService;

    @GetMapping("/salary-analysis")
    public ResponseEntity<List<SalaryAnalysisDTO>> getPlayersWithSalaryAnalysis() {
        List<SalaryAnalysisDTO> salaryAnalysis = salaryAnalysisService.getSalaryAnalysis();

        if (salaryAnalysis.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(salaryAnalysis);
    }

}
