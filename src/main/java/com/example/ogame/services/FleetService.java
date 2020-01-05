package com.example.ogame.services;

import com.example.ogame.datasource.FleetDataAccess;
import com.example.ogame.models.fleet.Ship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class FleetService {

    private final FleetDataAccess fleetDataAccess;
    private final Logger logger = LoggerFactory.getLogger(FleetService.class);

    public FleetService(FleetDataAccess fleetDataAccess) {
        this.fleetDataAccess = fleetDataAccess;
    }

    public List<Ship> getFleet(UUID userId) {
        return fleetDataAccess.selectFleet(userId);
    }

    public Ship getShipByName(UUID userID, String shipName) {
        return fleetDataAccess.selectShip(userID, shipName);
    }

    public void buildShip(UUID userID, String ship_name, int amount) {
        // Logic is possible to build ship
    }
}
