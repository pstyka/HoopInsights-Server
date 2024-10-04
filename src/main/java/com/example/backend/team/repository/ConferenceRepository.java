package com.example.backend.team.repository;

import com.example.backend.team.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    Optional<Conference> findByNameIgnoreCase(String name);
}