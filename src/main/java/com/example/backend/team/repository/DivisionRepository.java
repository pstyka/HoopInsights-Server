package com.example.backend.team.repository;

import com.example.backend.team.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DivisionRepository extends JpaRepository<Division, Long> {
    Optional<Division> findByNameIgnoreCase(String name);
}