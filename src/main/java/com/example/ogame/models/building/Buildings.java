package com.example.ogame.models.building;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Buildings {

    private final UUID buildings_id;

    // Resource Building
    private final UUID b_metal_id;
    private final UUID b_cristal_id;
    private final UUID b_deuterium_id;

    public Buildings(@JsonProperty("buildings_id") UUID buildings_id,
                     @JsonProperty("b_metal_id") UUID b_metal_id,
                     @JsonProperty("b_cristal_id") UUID b_cristal_id,
                     @JsonProperty("b_deuterium_id") UUID b_deuterium_id
    ) {
        this.buildings_id = buildings_id;
        this.b_metal_id = b_metal_id;
        this.b_cristal_id = b_cristal_id;
        this.b_deuterium_id = b_deuterium_id;
    }

    public UUID getBuildings_id() {
        return buildings_id;
    }

    public UUID getB_metal_id() {
        return b_metal_id;
    }

    public UUID getB_cristal_id() {
        return b_cristal_id;
    }

    public UUID getB_deuterium_id() {
        return b_deuterium_id;
    }
}
