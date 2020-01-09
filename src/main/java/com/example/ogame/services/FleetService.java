package com.example.ogame.services;

import com.example.ogame.datasource.FleetDataAccess;
import com.example.ogame.models.Resources;
import com.example.ogame.models.fleet.Ship;
import com.example.ogame.utils.fleet.FleetRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class FleetService {
    private final Logger logger = LoggerFactory.getLogger(FleetService.class);

    private final FleetDataAccess fleetDataAccess;
    private final ResourceService resourceService;
    private final FleetRule fleetRule;

    @Autowired
    public FleetService(FleetDataAccess fleetDataAccess,
                        ResourceService resourceService,
                        FleetRule fleetRule) {
        this.fleetDataAccess = fleetDataAccess;
        this.resourceService = resourceService;
        this.fleetRule = fleetRule;
    }

    public List<Ship> getFleet(UUID userId) {
        fleetRule.verifyUser(userId);
        return fleetDataAccess.selectFleet(userId);
    }

    public Ship getShipByName(UUID userId, String shipName) {
        fleetRule.verifyFleetApi(userId, shipName);
        return fleetDataAccess.selectShip(userId, shipName);
    }

    public int buildShip(UUID userID, String shipName, int amount) {
        fleetRule.verifyFleetApi(userID, shipName);
        Resources resources = resourceService.getResources(userID);
        Ship ship = fleetDataAccess.selectShip(userID, shipName);
        int build = buildShip(resources, ship, amount);

        if (build != 0) {
            ship.increaseAmountOfShips(build);
            fleetDataAccess.updateShip(ship);
        }
        logger.info(build + " " + ship.getName() + " has been built");
        return build;
    }

    private int buildShip(Resources res, Ship ship, int amount) {
        int metalCost = ship.getMetalCost();
        int cristalCost = ship.getCristalCost();
        int deuteriumCost = ship.getDeuteriumCost();

        for (int i = 0; i < amount; i++) {

            if (res.getMetal() < metalCost || res.getCristal() < cristalCost || res.getDeuterium() < deuteriumCost) {
                amount = i;
                break;
            } else {
                res.utilizeForBuild(metalCost, cristalCost, deuteriumCost);
            }
        }
        resourceService.updateResources(res);
        return amount;
    }
}