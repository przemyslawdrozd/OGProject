package com.example.ogame.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserInstance {

    private final UUID user_id;
    private final UUID resource_id;
    private final UUID buildings_id;
    // TODO 14 add fleet id
    private final UUID fleet_id;

    public UserInstance(@JsonProperty("user_id") UUID user_id,
                        @JsonProperty("resource_id") UUID resource_id,
                        @JsonProperty("buildings_id") UUID buildings_id,
                        @JsonProperty("fleet_id") UUID fleet_id
    ) {

        this.user_id = user_id;
        this.resource_id = resource_id;
        this.buildings_id = buildings_id;
        this.fleet_id = fleet_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public UUID getResource_id() {
        return resource_id;
    }

    public UUID getBuildings_id() {
        return buildings_id;
    }

    public UUID getFleet_id() {
        return fleet_id;
    }
}
