package com.example.backend.injury.service;

import com.example.backend.injury.repository.InjuryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InjuryScraperService {

    @Value("${flask.base-url}")
    private String flaskBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String scrapeInjuries() {
        String flaskEndpoint = flaskBaseUrl + "/scrape-cbs-injuries";
        return restTemplate.postForObject(flaskEndpoint, null, String.class);
    }

}
