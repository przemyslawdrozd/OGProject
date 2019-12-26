package com.example.ogame.models.building;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuildingInstance {

    private List<UUID> uuidList;

    private final UUID buildings_id;

    // Resource Building
    private final UUID b_metal_id;
    private final UUID b_cristal_id;
    private final UUID b_deuterium_id;

    // Common Buildings
    private final UUID b_shipyard_id;

    public BuildingInstance(@JsonProperty("buildings_id") UUID buildings_id,
                            @JsonProperty("b_metal_id") UUID b_metal_id,
                            @JsonProperty("b_cristal_id") UUID b_cristal_id,
                            @JsonProperty("b_deuterium_id") UUID b_deuterium_id,
                            @JsonProperty("b_shipyard_id") UUID b_shipyard_id
    ) {
        this.buildings_id = buildings_id;
        this.b_metal_id = b_metal_id;
        this.b_cristal_id = b_cristal_id;
        this.b_deuterium_id = b_deuterium_id;
        this.b_shipyard_id = b_shipyard_id;

        initUUIDs();
    }

    private void initUUIDs() {
        this.uuidList = new ArrayList<>();
        uuidList.add(this.b_metal_id);
        uuidList.add(this.b_cristal_id);
        uuidList.add(this.b_deuterium_id);
        uuidList.add(this.b_shipyard_id);
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

    public UUID getB_shipyard_id() {
        return b_shipyard_id;
    }

    public UUID getBuildingIdByName(String buildingName) {
        switch (buildingName) {
            case "Metal Mine":
                return this.b_metal_id;
            case "Cristal Mine":
                return this.b_cristal_id;
            case "Deuterium Synthesizer":
                return this.b_deuterium_id;
            case "Shipyard":
                return this.b_shipyard_id;
            default:
                return null;
        }
    }

    public List<UUID> getUuidList() {
        return uuidList;
    }
}
