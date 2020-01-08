package com.example.ogame.models.facilities;

import java.util.UUID;

public class Building {

    private final UUID building_id;
    private final String name;
    private int level;
    private int neededMetal;
    private int neededCristal;
    private int neededDeuterium;
    private int buildTime;
    private int productionPerHour;

    public Building(UUID building_id,
                    String name,
                    int level,
                    int neededMetal,
                    int neededCristal,
                    int neededDeuterium,
                    int buildTime,
                    int productionPerHour) {

        this.building_id = building_id;
        this.name = name;
        this.level = level;
        this.neededMetal = neededMetal;
        this.neededCristal = neededCristal;
        this.neededDeuterium = neededDeuterium;
        this.buildTime = buildTime;
        this.productionPerHour = productionPerHour;
    }

    public void lvlUpBuilding(){
        double increaseCost = 1.75;
        this.level++;

        this.neededMetal *= increaseCost;
        this.neededCristal *= increaseCost;
        this.neededDeuterium *= increaseCost;

        if (productionPerHour != 0) {
            this.productionPerHour *= 1.25;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNeededMetal() {
        return neededMetal;
    }

    public void setNeededMetal(int neededMetal) {
        this.neededMetal = neededMetal;
    }

    public int getNeededCristal() {
        return neededCristal;
    }

    public void setNeededCristal(int neededCristal) {
        this.neededCristal = neededCristal;
    }

    public int getNeededDeuterium() {
        return neededDeuterium;
    }

    public void setNeededDeuterium(int neededDeuterium) {
        this.neededDeuterium = neededDeuterium;
    }

    public String getName() {
        return name;
    }

    public UUID getBuilding_id() {
        return building_id;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(int buildTime) {
        this.buildTime = buildTime;
    }

    public int getProductionPerHour() {
        return productionPerHour;
    }

    public void setProductionPerHour(int productionPerHour) {
        this.productionPerHour = productionPerHour;
    }
}
