package com.example.backend.shoe.repository;

import com.example.backend.shoe.entity.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoeRepository extends JpaRepository<Shoe, Long> {
    Optional<Shoe> findByBrandAndModel(String brand, String model);
}
