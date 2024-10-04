package com.example.backend.player.service;

import com.example.backend.player.dto.PlayerApiResponseDTO;
import com.example.backend.player.entity.Player;
import com.example.backend.player.repository.PlayerRepository;
import com.example.backend.team.entity.Team;
import com.example.backend.team.repository.TeamRepository;
import com.example.backend.team.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerApiService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamsService teamsService;

    @Value("${api.rapidapi.host}")
    private String apiHost;

    @Value("${api.rapidapi.key}")
    private String apiKey;

    public PlayerApiService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<PlayerApiResponseDTO.PlayerDTO> getPlayersForTeam(String season, Integer teamApiId) {
        WebClient webClient = webClientBuilder.baseUrl("https://" + apiHost).build();

        PlayerApiResponseDTO response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/players")
                        .queryParam("team", teamApiId)
                        .queryParam("season", season)
                        .build())
                .header("x-rapidapi-host", apiHost)
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(PlayerApiResponseDTO.class)
                .block(); // Synchronize, use `subscribe()` for async

        return response != null ? response.getResponse() : Collections.emptyList();
    }

    private Date convertToDate(String birthDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void savePlayersForTeam(String season, Integer teamApiId) {
        Team team = teamRepository.findByApiId(teamApiId)
                .orElseThrow(() -> new RuntimeException("Team with API ID " + teamApiId + " not found"));

        List<PlayerApiResponseDTO.PlayerDTO> playersFromApi = getPlayersForTeam(season, teamApiId);

        for (PlayerApiResponseDTO.PlayerDTO playerDTO : playersFromApi) {
            Optional<Player> existingPlayer = playerRepository.findByFirstNameAndLastNameIsLikeIgnoreCase(playerDTO.getFirstname(), playerDTO.getLastname());

            Player player;
            if (existingPlayer.isPresent()) {
                player = existingPlayer.get();
            } else {
                player = new Player();
            }

            player.setApiId(playerDTO.getId());

            player.setFirstName(playerDTO.getFirstname() != null ? playerDTO.getFirstname() : null);
            player.setLastName(playerDTO.getLastname() != null ? playerDTO.getLastname() : null);

            if (playerDTO.getBirth() != null) {
                player.setBirthDate(playerDTO.getBirth().getDate() != null ? convertToDate(playerDTO.getBirth().getDate()) : null);
                player.setCountry(playerDTO.getBirth().getCountry() != null ? playerDTO.getBirth().getCountry() : null);
            } else {
                player.setBirthDate(null);
                player.setCountry(null);
            }

            if (playerDTO.getHeight() != null) {
                String heightInch = playerDTO.getHeight().getFeets() != null && playerDTO.getHeight().getInches() != null
                        ? playerDTO.getHeight().getFeets() + "-" + playerDTO.getHeight().getInches()
                        : null;
                player.setHeightInch(heightInch);
                player.setHeightCm(playerDTO.getHeight().getMeters() != null ? playerDTO.getHeight().getMeters() : null);
            } else {
                player.setHeightInch(null);
                player.setHeightCm(null);
            }

            if (playerDTO.getWeight() != null) {
                player.setWeightLbs(playerDTO.getWeight().getPounds() != null ? playerDTO.getWeight().getPounds() : null);
                player.setWeightKg(playerDTO.getWeight().getKilograms() != null ? playerDTO.getWeight().getKilograms() : null);
            } else {
                player.setWeightLbs(null);
                player.setWeightKg(null);
            }

            if (playerDTO.getLeagues() != null && playerDTO.getLeagues().getStandard() != null) {
                player.setJerseyNumber(playerDTO.getLeagues().getStandard().getJersey() != null
                        ? playerDTO.getLeagues().getStandard().getJersey()
                        : null);
                player.setIsActive(playerDTO.getLeagues().getStandard().getActive() != null
                        ? playerDTO.getLeagues().getStandard().getActive()
                        : null);
                player.setPosition(playerDTO.getLeagues().getStandard().getPos() != null
                        ? playerDTO.getLeagues().getStandard().getPos()
                        : null);
            } else {
                player.setJerseyNumber(null);
                player.setIsActive(null);
                player.setPosition(null);
            }

            player.setTeam(team);

            playerRepository.save(player);
        }
    }

    public void getAllPlayersByYear(String year) {
        try {
            List<Integer> teamIds = teamsService.getTeamApiIds();
            for(Integer teamId : teamIds){

                Thread.sleep(5000);
                savePlayersForTeam(year, teamId);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




}
