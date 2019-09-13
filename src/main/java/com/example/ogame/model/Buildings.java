package com.example.ogame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Buildings {

    // Resources building?
    private final UUID buildings_id;

    private List<Building> buildings;

    public Buildings(@JsonProperty("buildings_id") UUID buildings_id) {
        this.buildings_id = buildings_id;
        this.buildings = new ArrayList<>();
    }

    public UUID getBuildings_id() {
        return buildings_id;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
