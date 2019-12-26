package com.example.ogame.models.building;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ResourceBuilding extends Building {

    private int productionPerHour;

    public ResourceBuilding(UUID building_id,
                            String name,
                            int level,
                            int neededMetal,
                            int neededCristal,
                            int neededDeuterium,
                            int buildTime,
                            @JsonProperty("production_per_hour") int productionPerHour) {
        super(building_id, name, level, neededMetal, neededCristal, neededDeuterium, buildTime);
        this.productionPerHour = productionPerHour;
    }

    @Override
    public void lvlUpBuilding() {
        super.lvlUpBuilding();
        this.productionPerHour *= 1.25;
    }

    public int getProductionPerHour() {
        return productionPerHour;
    }

    public void setProductionPerHour(int productionPerHour) {
        this.productionPerHour = productionPerHour;
    }
}
