package com.example.backend.team.repository;

import com.example.backend.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> findByAbb(String abb);

    Optional<Team> findByApiId(Integer apiId);
    List<Team> findByConferenceId(Long conferenceId);
    List<Team> findByDivisionId(Long divisionId);

    List<Team> findByNameIsLikeIgnoreCase(String name);

    List<Team> findByNameIsLikeIgnoreCaseAndConferenceId(String name, Long conferenceId);

    List<Team> findByNameIsLikeIgnoreCaseAndDivisionId(String name, Long divisionId);

    List<Team> findByConferenceIdAndDivisionId(Long conferenceId, Long divisionId);

    List<Team> findByNameIsLikeIgnoreCaseAndConferenceIdAndDivisionId(String name, Long conferenceId, Long divisionId);

    Optional<Team> findByName(String name);
}
