package com.example.ogame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserInstance {

    private final UUID user_id;
    private final UUID resource_id;

    public UserInstance(@JsonProperty("user_id") UUID user_id,
                        @JsonProperty("resource_id") UUID resource_id) {

        this.user_id = user_id;
        this.resource_id = resource_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public UUID getResource_id() {
        return resource_id;
    }
}
