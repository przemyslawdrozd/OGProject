package com.example.ogame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Building {

    private final UUID building_id;

    @NotNull
    private final String name;

    @NotNull
    private int level;

    @NotNull
    private int neededMetal;

    @NotNull
    private int neededCristal;

    @NotNull
    private int neededDeuterium;

    // TODO Time to build ?

    public Building(@JsonProperty("building_id") UUID building_id,
                    @JsonProperty("name") String name,
                    @JsonProperty("level") int level,
                    @JsonProperty("neededMetal") int neededMetal,
                    @JsonProperty("neededCristal") int neededCristal,
                    @JsonProperty("neededDeuterium") int neededDeuterium) {

        this.building_id = building_id;
        this.name = name;
        this.level = level;
        this.neededMetal = neededMetal;
        this.neededCristal = neededCristal;
        this.neededDeuterium = neededDeuterium;
    }

    public static void lvlUpBuilding(Building building){

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
}
