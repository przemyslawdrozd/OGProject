package com.example.ogame.models;

import java.util.UUID;

public class UserInstance {

    private final UUID userId;
    private final UUID resourceId;
    private final UUID facilitiesId;
    private final UUID fleetId;

    public UserInstance(UUID userId,
                        UUID resourceId,
                        UUID facilitiesId,
                        UUID fleetId
    ) {

        this.userId = userId;
        this.resourceId = resourceId;
        this.facilitiesId = facilitiesId;
        this.fleetId = fleetId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public UUID getFacilitiesId() {
        return facilitiesId;
    }

    public UUID getFleetId() {
        return fleetId;
    }
}
