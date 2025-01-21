package com.example.backend.scraper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShoeScraperService {

    @Value("${flask.base-url}")
    private String flaskBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String startScraping() {
        String flaskEndpoint = flaskBaseUrl + "/shoes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(flaskEndpoint, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }
}
