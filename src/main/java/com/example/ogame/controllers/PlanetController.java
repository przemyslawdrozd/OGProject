package com.example.ogame.controllers;

import com.example.ogame.models.Planet;
import com.example.ogame.services.PlanetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/planet-api")
public class PlanetController {
    private final Logger logger = LoggerFactory.getLogger(PlanetController.class);

    private PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping
    public List<Planet> getGalaxy(){
        return planetService.getGalaxy();
    }


    @GetMapping("/{id}")
    public Planet getPlanet(@PathVariable("id") String id) {
        UUID userId = UUID.fromString(id);
        return planetService.getUserPlanet(userId);
    }




}
