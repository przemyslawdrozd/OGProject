package com.example.ogame.models.facilities;

import com.example.ogame.utils.fleet.BuildingName;
import java.util.UUID;

public class Building {

    private UUID buildingId;
    private final BuildingName name;
    private int level;
    private int neededMetal;
    private int neededCristal;
    private int neededDeuterium;
    private int buildTime;
    private int productionPerHour;

    public Building(UUID buildingId,
                    BuildingName name,
                    int level,
                    int neededMetal,
                    int neededCristal,
                    int neededDeuterium,
                    int buildTime,
                    int productionPerHour) {

        this.buildingId = buildingId;
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
            this.productionPerHour *= 2;
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

    public BuildingName getName() {
        return name;
    }

    public UUID getBuildingId() {
        return buildingId;
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
