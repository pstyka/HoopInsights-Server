package com.example.backend.shoe.repository;

import com.example.backend.shoe.entity.ShoeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeHistoryRepository extends JpaRepository<ShoeHistory, Long> {
}
