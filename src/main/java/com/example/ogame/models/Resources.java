package com.example.ogame.models;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Resources {

    private final UUID resourceId;

    @NotBlank
    private int metal;

    @NotBlank
    private int cristal;

    @NotBlank
    private int deuterium;

    public Resources(UUID resourceId, int metal, int cristal, int deuterium) {
        this.resourceId = resourceId;
        this.metal = metal;
        this.cristal = cristal;
        this.deuterium = deuterium;
    }

    public void updatePerSec(double metal, double cristal, double deuterium) {
        this.metal += metal;
        this.cristal += cristal;
        this.deuterium += deuterium;
    }

    public void utilizeForBuild(int metal, int cristal, int deuterium) {
        this.metal -= metal;
        this.cristal -= cristal;
        this.deuterium -= deuterium;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getCristal() {
        return cristal;
    }

    public void setCristal(int cristal) {
        this.cristal = cristal;
    }

    public int getDeuterium() {
        return deuterium;
    }

    public void setDeuterium(int deuterium) {
        this.deuterium = deuterium;
    }
}
