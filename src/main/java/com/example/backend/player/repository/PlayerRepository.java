package com.example.backend.player.repository;

import com.example.backend.player.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {

    Optional<Player> findByFirstNameAndLastNameIsLikeIgnoreCase(String firstName, String lastName);

    Page<Player> findByFirstNameIsLikeIgnoreCase(String firstName, Pageable pageable);

    Page<Player> findByLastNameIsLikeIgnoreCase(String lastName, Pageable pageable);

    Page<Player> findByPositionIsLikeIgnoreCase(String position, Pageable pageable);


    Page<Player> findByFirstNameAndLastNameIsLikeIgnoreCase(String firstName, String lastName, Pageable pageable);

    Page<Player> findByFirstNameAndLastNameAndPositionIsLikeIgnoreCase(String firstName, String lastName, String position, Pageable pageable);
}
