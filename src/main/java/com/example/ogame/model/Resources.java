package com.example.ogame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Resources {

    private final UUID resource_id;

    @NotBlank
    private int metal;

    @NotBlank
    private int cristal;

    @NotBlank
    private int deuterium;

    public Resources(@JsonProperty("resource_id") UUID resource_id,
                     @JsonProperty("metal") int metal,
                     @JsonProperty("cristal") int cristal,
                     @JsonProperty("deuterium") int deuterium) {

        this.resource_id = resource_id;
        this.metal = metal;
        this.cristal = cristal;
        this.deuterium = deuterium;
    }

    public UUID getResource_id() {
        return resource_id;
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
