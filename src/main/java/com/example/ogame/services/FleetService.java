package com.example.ogame.services;

import com.example.ogame.datasource.FleetDataAccess;
import com.example.ogame.models.Resources;
import com.example.ogame.models.fleet.Ship;
import com.example.ogame.utils.FleetRoleImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class FleetService {

    private final FleetDataAccess fleetDataAccess;
    private final ResourceService resourceService;
    private final FleetRoleImpl fleetRole;
    private final Logger logger = LoggerFactory.getLogger(FleetService.class);

    public FleetService(FleetDataAccess fleetDataAccess,
                        ResourceService resourceService,
                        FleetRoleImpl fleetRole) {
        this.fleetDataAccess = fleetDataAccess;
        this.resourceService = resourceService;
        this.fleetRole = fleetRole;
    }

    public List<Ship> getFleet(UUID userId) {
        fleetRole.verifyFleetApi(userId);
        return fleetDataAccess.selectFleet(userId);
    }

    public Ship getShipByName(UUID userId, String shipName) {
        fleetRole.verifyFleetApi(userId, shipName);
        return fleetDataAccess.selectShip(userId, shipName);
    }

    public int buildShip(UUID userID, String ship_name, int amount) {
        fleetRole.verifyFleetApi(userID, ship_name);
        Resources resources = resourceService.getResources(userID);
        Ship ship = fleetDataAccess.selectShip(userID, ship_name);
        int build = fleetRole.buildShip(resources, ship, amount);

        if (build != 0) {
            ship.increaseAmountOfShips(build);
            fleetDataAccess.updateShip(ship);
        }
        logger.info(build + " " + ship.getName() + " has been built");
        return build;
    }
}