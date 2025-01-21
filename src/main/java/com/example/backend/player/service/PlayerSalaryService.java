package com.example.backend.player.service;

import com.example.backend.player.dto.PlayerSeasonStatsDTO;
import com.example.backend.player.dto.SalaryAnalysisDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.entity.stats.PlayerSeasonStats;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.player.repository.salary.SalaryRepository;
import com.example.backend.standings.dto.TeamStandingsDTO;
import com.example.backend.standings.entity.TeamStandings;
import com.example.backend.standings.repository.TeamStandingsRepository;
import com.example.backend.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerSalaryService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private TeamStandingsRepository teamStandingsRepository;

    public List<SalaryAnalysisDTO> getSalaryAnalysis() {
        List<Player> playersWithSalaries = playerRepository.findAllWithSalaries();

        return playersWithSalaries.stream()
                .map(player -> {
                    PlayerSeasonStats seasonStats2024_25 = player.getPlayerSeasonStats().stream()
                            .filter(stats -> "2024-25".equals(stats.getSeason()))
                            .findFirst()
                            .orElse(null);

                    if (seasonStats2024_25 == null) {
                        return null;
                    }

                    PlayerSeasonStatsDTO seasonStatsDTO = PlayerSeasonStatsDTO.builder()
                            .season(seasonStats2024_25.getSeason())
                            .gamesPlayed(seasonStats2024_25.getGamesPlayed())
                            .gamesStarted(seasonStats2024_25.getGamesStarted())
                            .minutesPlayed(seasonStats2024_25.getMinutesPlayed())
                            .fieldGoals(seasonStats2024_25.getFieldGoals())
                            .fieldGoalsAttempts(seasonStats2024_25.getFieldGoalsAttempts())
                            .fieldGoalsPercent(seasonStats2024_25.getFieldGoalsPercent())
                            .threePointersMade(seasonStats2024_25.getThreePointersMade())
                            .threePointersAttempts(seasonStats2024_25.getThreePointersAttempts())
                            .threePointersPercent(seasonStats2024_25.getThreePointersPercent())
                            .twoPointersAttempts(seasonStats2024_25.getTwoPointersAttempts())
                            .twoPointersPercent(seasonStats2024_25.getTwoPointersPercent())
                            .freeThrowAttempts(seasonStats2024_25.getFreeThrowAttempts())
                            .freeThrowPercent(seasonStats2024_25.getFreeThrowPercent())
                            .offensiveRebounds(seasonStats2024_25.getOffensiveRebounds())
                            .defensiveRebounds(seasonStats2024_25.getDefensiveRebounds())
                            .rebounds(seasonStats2024_25.getRebounds())
                            .assists(seasonStats2024_25.getAssists())
                            .steals(seasonStats2024_25.getSteals())
                            .blocks(seasonStats2024_25.getBlocks())
                            .turnovers(seasonStats2024_25.getTurnovers())
                            .fouls(seasonStats2024_25.getFouls())
                            .points(seasonStats2024_25.getPoints())
                            .build();

                    TeamStandingsDTO teamStandingsDTO = null;
                    TeamStandings teamStandings = player.getTeam().getTeamStandings();
                    if (teamStandings != null) {
                        teamStandingsDTO = new TeamStandingsDTO(
                                teamStandings.getTeam().getName(),
                                teamStandings.getWins(),
                                teamStandings.getLosses(),
                                teamStandings.getWinPercentage(),
                                teamStandings.getGamesBehind(),
                                teamStandings.getPointsPerGame(),
                                teamStandings.getOpponentPointsPerGame(),
                                teamStandings.getSimpleRatingSystem(),
                                teamStandings.getConference().getName(),
                                teamStandings.getTeam().getLogoUrl()
                        );
                    }

                    return SalaryAnalysisDTO.builder()
                            .firstName(player.getFirstName())
                            .lastName(player.getLastName())
                            .teamName(player.getTeam().getName())
                            .position(player.getPosition())
                            .salary(player.getSalary() != null ? player.getSalary().getSalary() : null)
                            .seasonStats(seasonStatsDTO)
                            .standings(teamStandingsDTO)
                            .build();
                })
                .filter(dto -> dto != null && dto.getSalary() != null)
                .collect(Collectors.toList());
    }


}
