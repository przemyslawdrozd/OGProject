package com.example.ogame.models;

import java.util.UUID;

public class Galaxy {

    private UUID planetId;
    private int galaxyPosition;
    private int planetarySystem;
    private int planetPosition;

    public Galaxy(UUID planetId, int galaxyPosition, int planetarySystem, int planetPosition) {
        this.planetId = planetId;
        this.galaxyPosition = galaxyPosition;
        this.planetarySystem = planetarySystem;
        this.planetPosition = planetPosition;
    }

    public UUID getPlanetId() {
        return planetId;
    }

    public void setPlanetId(UUID planetId) {
        this.planetId = planetId;
    }

    public int getGalaxyPosition() {
        return galaxyPosition;
    }

    public void setGalaxyPosition(int galaxyPosition) {
        this.galaxyPosition = galaxyPosition;
    }

    public int getPlanetarySystem() {
        return planetarySystem;
    }

    public void setPlanetarySystem(int planetarySystem) {
        this.planetarySystem = planetarySystem;
    }

    public int getPlanetPosition() {
        return planetPosition;
    }

    public void setPlanetPosition(int planetPosition) {
        this.planetPosition = planetPosition;
    }
}
