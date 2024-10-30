package com.example.backend.standings.service;

import com.example.backend.standings.dto.TeamStandingsDTO;
import com.example.backend.standings.entity.TeamStandings;
import com.example.backend.standings.repository.TeamStandingsRepository;
import com.example.backend.team.entity.Conference;
import com.example.backend.team.entity.Team;
import com.example.backend.team.repository.ConferenceRepository;
import com.example.backend.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamStandingsService {

    private final TeamStandingsRepository teamStandingsRepository;
    private final TeamRepository teamRepository;
    private final ConferenceRepository conferenceRepository;
    private final RestTemplate restTemplate;


    @Transactional
    public void saveOrUpdateStandings() {
        String flaskUrl = "http://flask-service:5000/standings";
        Map<String, List<Map<String, Object>>> standingsData = restTemplate.postForObject(flaskUrl, null, Map.class);

        if (standingsData == null) {
            throw new RuntimeException("Nie udało się pobrać danych z Flaska");
        }

        for (Map.Entry<String, List<Map<String, Object>>> entry : standingsData.entrySet()) {
            String conferenceName = entry.getKey();
            List<Map<String, Object>> teamsData = entry.getValue();

            Conference conference = conferenceRepository.findByNameIgnoreCase(conferenceName)
                    .orElseThrow(() -> new RuntimeException("Nie znaleziono konferencji: " + conferenceName));

            for (Map<String, Object> teamData : teamsData) {
                String teamName = (String) teamData.get("team_name");
                Team team = teamRepository.findByName(teamName)
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono zespołu: " + teamName));

                TeamStandings teamStandings = teamStandingsRepository.findByTeamAndConference(team, conference)
                        .orElse(new TeamStandings());

                teamStandings.setTeam(team);
                teamStandings.setConference(conference);
                teamStandings.setWins(Integer.parseInt((String) teamData.get("wins")));
                teamStandings.setLosses(Integer.parseInt((String) teamData.get("losses")));
                teamStandings.setWinPercentage(Double.parseDouble((String) teamData.get("win_loss_pct")));
                teamStandings.setGamesBehind(teamData.get("gb").toString());
                teamStandings.setPointsPerGame(Double.parseDouble((String) teamData.get("pts_per_g")));
                teamStandings.setOpponentPointsPerGame(Double.parseDouble((String) teamData.get("opp_pts_per_g")));
                teamStandings.setSimpleRatingSystem(Double.parseDouble((String) teamData.get("srs")));

                teamStandingsRepository.save(teamStandings);
            }
        }
    }
    public List<TeamStandingsDTO> getStandings() {
        List<TeamStandings> standings = teamStandingsRepository.findAll();

        return standings.stream().map(teamStanding -> {
            TeamStandingsDTO dto = new TeamStandingsDTO();
            dto.setTeamName(teamStanding.getTeam().getName());
            dto.setWins(teamStanding.getWins());
            dto.setLosses(teamStanding.getLosses());
            dto.setWinPercentage(teamStanding.getWinPercentage());
            dto.setGamesBehind(teamStanding.getGamesBehind());
            dto.setPointsPerGame(teamStanding.getPointsPerGame());
            dto.setOpponentPointsPerGame(teamStanding.getOpponentPointsPerGame());
            dto.setSimpleRatingSystem(teamStanding.getSimpleRatingSystem());
            dto.setConferenceName(teamStanding.getConference().getName());
            dto.setLogoUrl(teamStanding.getTeam().getLogoUrl());  // Dodaj logo URL
            return dto;
        }).collect(Collectors.toList());
    }


}
