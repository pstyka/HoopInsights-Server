package com.example.backend.shoe.controller;


import com.example.backend.shoe.service.ShoeScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shoes")
public class ShoeScraperController {

    @Autowired
    private ShoeScraperService scrapingService;

    @PostMapping("/scrape-shoes")
    public ResponseEntity<String> startScraping() {
        try {
            String response = scrapingService.startScraping();
            return ResponseEntity.ok("Proces scrapowania w Flask został uruchomiony. Odpowiedź: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Błąd podczas wywoływania Flask: " + e.getMessage());
        }
    }
}
