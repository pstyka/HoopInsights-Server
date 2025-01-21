package com.example.backend.injury.repository;

import com.example.backend.injury.entity.Injury;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjuryRepository extends JpaRepository<Injury, Long> {
}
