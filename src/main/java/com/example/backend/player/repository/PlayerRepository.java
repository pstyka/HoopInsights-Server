package com.example.backend.player.repository;

import com.example.backend.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {

    Optional<Player> findByFirstNameAndLastName(String firstName, String lastName);
}
