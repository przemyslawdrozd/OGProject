package com.example.ogame.services;

import com.example.ogame.datasource.PlanetDataAccess;
import com.example.ogame.models.Planet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanetService {
    private final Logger logger = LoggerFactory.getLogger(PlanetService.class);

    private PlanetDataAccess planetDataAccess;

    @Autowired
    public PlanetService(PlanetDataAccess planetDataAccess) {
        this.planetDataAccess = planetDataAccess;
    }

    public List<Planet> getGalaxy() {
        return planetDataAccess.selectGalaxy();
    }

    public Planet getUserPlanet(UUID userId) {
        return planetDataAccess.selectPlanet(userId);
    }
}
