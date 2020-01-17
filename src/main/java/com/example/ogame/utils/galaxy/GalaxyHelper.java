package com.example.ogame.utils.galaxy;

import com.example.ogame.models.Planet;

import java.util.UUID;

public class GalaxyHelper {

    public static Object[] insertPlanet(UUID planetId, Planet planet) {
        return new Object[] {
                planetId,
                planet.getGalaxyPosition(),
                planet.getPlanetarySystem(),
                planet.getPlanetPosition()
        };
    }
}
