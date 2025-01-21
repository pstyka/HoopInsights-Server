package com.example.backend.player.repository;

import com.example.backend.player.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    @Query("SELECT p FROM Player p JOIN FETCH p.salary s WHERE s IS NOT NULL")
    List<Player> findAllWithSalaries();

    Optional<Player> findByFirstNameAndLastNameIsLikeIgnoreCase(String firstName, String lastName);

    Page<Player> findByFirstNameIsLikeIgnoreCase(String firstName, Pageable pageable);

    Page<Player> findByLastNameIsLikeIgnoreCase(String lastName, Pageable pageable);

    Page<Player> findByPositionIsLikeIgnoreCase(String position, Pageable pageable);


    Page<Player> findByFirstNameAndLastNameIsLikeIgnoreCase(String firstName, String lastName, Pageable pageable);

    Page<Player> findByFirstNameAndLastNameAndPositionIsLikeIgnoreCase(String firstName, String lastName, String position, Pageable pageable);

    @Query("SELECT p FROM Player p JOIN p.team t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :teamName, '%'))")
    Page<Player> findPlayersByTeamName(@Param("teamName") String teamName, Pageable pageable);


    // Znajdź zawodników po wzroście (inch)
    Page<Player> findPlayersByHeightInch(String heightInch, Pageable pageable);

    // Znajdź zawodników po wadze (lbs)
    Page<Player> findPlayersByWeightLbs(String weightLbs, Pageable pageable);

    List<Player> findByCurrentShoeIsNotNull();
}
