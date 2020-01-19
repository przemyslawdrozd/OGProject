package com.example.ogame.services;

import com.example.ogame.datasource.PlanetDataAccess;
import com.example.ogame.models.Planet;
import com.example.ogame.utils.galaxy.PlanetRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PlanetService {
    private final Logger logger = LoggerFactory.getLogger(PlanetService.class);

    private final PlanetDataAccess planetDataAccess;
    private final PlanetRule planetRule;

    @Autowired
    public PlanetService(PlanetDataAccess planetDataAccess, PlanetRule planetRule) {
        this.planetDataAccess = planetDataAccess;
        this.planetRule = planetRule;
    }

    public List<Planet> getGalaxy() {
        return planetDataAccess.selectGalaxy();
    }

    public Planet getUserPlanet(UUID userId) {
        planetRule.verifyUser(userId);
        return planetDataAccess.selectPlanet(userId);
    }
}
