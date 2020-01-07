package com.example.ogame.utils;

import com.example.ogame.models.Resources;
import com.example.ogame.models.fleet.Ship;

import java.util.UUID;

public interface FleetRule {

    int buildShip(Resources res, Ship ship, int amount);
    void verifyFleetApi(UUID userId, String shipName);
    void verifyFleetApi(UUID userId);
}
