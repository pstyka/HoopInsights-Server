package com.example.backend.shoe.controller;

import com.example.backend.shoe.entity.Shoe;
import com.example.backend.shoe.repository.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shoes")
public class ShoeController {

    @Autowired
    private ShoeRepository shoeRepository;


    @PostMapping
    public Shoe addShoe(@RequestBody Shoe shoe) {
        return shoeRepository.save(shoe);
    }

    @GetMapping
    public List<Shoe> getAllShoes() {
        return shoeRepository.findAll();
    }

}
