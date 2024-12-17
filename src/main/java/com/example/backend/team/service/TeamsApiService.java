package com.example.backend.team.service;

import com.example.backend.team.dto.TeamAbbAndNameDTO;
import com.example.backend.team.dto.TeamApiResponseDTO;
import com.example.backend.team.entity.Team;
import com.example.backend.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TeamsApiService {

    private final WebClient.Builder webClientBuilder;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamsService teamsService;

    @Value("${api.rapidapi.host}")
    private String apiHost;

    @Value("${api.rapidapi.key}")
    private String apiKey;

    public TeamsApiService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    public List<TeamApiResponseDTO.TeamDTO> getTeamsByAbbreviation(String abbreviation) {
        WebClient webClient = webClientBuilder.baseUrl("https://" + apiHost).build();

        TeamApiResponseDTO response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/teams")
                        .queryParam("code", abbreviation)
                        .build())
                .header("x-rapidapi-host", apiHost)
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(TeamApiResponseDTO.class)
                .block();

        if (response == null || response.getResponse() == null) {
            return Collections.emptyList();
        }

        return response.getResponse();
    }

    public TeamApiResponseDTO.TeamDTO getTeamByAbbreviation(String abbreviation) {
        WebClient webClient = webClientBuilder.baseUrl("https://" + apiHost).build();

        TeamApiResponseDTO response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/teams")
                        .queryParam("code", abbreviation)
                        .build())
                .header("x-rapidapi-host", apiHost)
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(TeamApiResponseDTO.class)
                .block();

        if (response != null && response.getResponse() != null && !response.getResponse().isEmpty()) {
            return response.getResponse().get(0);
        }

        return null;
    }
    public void updateTeamsFromApi() {
        List<TeamAbbAndNameDTO> existingTeams = teamsService.getTeamsAbbList();

        for (TeamAbbAndNameDTO teamDTO : existingTeams) {
            TeamApiResponseDTO.TeamDTO updatedTeamData = getTeamByAbbreviation(teamDTO.getAbb());

            if (updatedTeamData != null) {
                Optional<Team> teamOpt = teamRepository.findByAbb(teamDTO.getAbb());

                if (teamOpt.isPresent()) {
                    Team team = new Team();
                    team.setName(updatedTeamData.getName());
                    team.setCity(updatedTeamData.getCity());
                    team.setLogoUrl(updatedTeamData.getLogoUrl());
                    team.setApiId(updatedTeamData.getId());

                    teamRepository.save(team);
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateTeamByAbbFromApi(String abb) {
       TeamApiResponseDTO.TeamDTO teamData = getTeamByAbbreviation(abb);

       if(teamData != null){
           Optional<Team> teamOpt = teamRepository.findByAbb(abb);

           if(teamOpt.isPresent()){
               Team team = new Team();
               team.setName(teamData.getName());
               team.setCity(teamData.getCity());
               team.setLogoUrl(teamData.getLogoUrl());
               team.setApiId(teamData.getId());

               teamRepository.save(team);
           } else {
               throw new EntityNotFoundException("Team with abbreviation " + abb + " not found");
           }
       }
    }

    public void saveTeamsByAbbreviation(String abbreviation) {
        List<TeamApiResponseDTO.TeamDTO> teams = getTeamsByAbbreviation(abbreviation);

        if (teams != null) {
            for (TeamApiResponseDTO.TeamDTO teamDTO : teams) {
                Team team = new Team();
                team.setAbb(teamDTO.getAbb());
                team.setName(teamDTO.getName());
                team.setCity(teamDTO.getCity());
                team.setLogoUrl(teamDTO.getLogoUrl());
                team.setApiId(teamDTO.getId());

                teamRepository.save(team);
            }
        } else {
            System.out.println("No teams found for abbreviation: " + abbreviation);
        }
    }


}
