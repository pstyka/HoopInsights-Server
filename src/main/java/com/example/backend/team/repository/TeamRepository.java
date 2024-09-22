package com.example.backend.team.repository;

import com.example.backend.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Team findByAbb(String abb);
    Optional<Team> findByApiId(Integer apiId);
}
