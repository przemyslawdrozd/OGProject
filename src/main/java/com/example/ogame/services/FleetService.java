package com.example.ogame.services;

import com.example.ogame.datasource.FleetDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.Resources;
import com.example.ogame.models.fleet.Ship;
import com.example.ogame.utils.ShipName;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class FleetService {

    private final FleetDataAccess fleetDataAccess;
    private final ResourceService resourceService;
    private final VerifyDataAccess verifyDataAccess;
    private final Logger logger = LoggerFactory.getLogger(FleetService.class);

    public FleetService(FleetDataAccess fleetDataAccess,
                        ResourceService resourceService,
                        VerifyDataAccess verifyDataAccess) {
        this.fleetDataAccess = fleetDataAccess;
        this.resourceService = resourceService;
        this.verifyDataAccess = verifyDataAccess;
    }

    public List<Ship> getFleet(UUID userId) {
        verifyFleetApi(userId);
        return fleetDataAccess.selectFleet(userId);
    }

    public Ship getShipByName(UUID userId, String shipName) {
        verifyFleetApi(userId, shipName);
        return fleetDataAccess.selectShip(userId, shipName);
    }

    public int buildShip(UUID userID, String ship_name, int amount) {
        verifyFleetApi(userID, ship_name);
        Resources resources = resourceService.getResources(userID);
        Ship ship = fleetDataAccess.selectShip(userID, ship_name);
        int build = verifyBuildShip(resources, ship, amount);

        if (build != 0) {
            ship.increaseAmountOfShips(build);
            fleetDataAccess.updateShip(ship);
        }
        logger.info(build + " " + ship.getName() + " has been built");
        return build;
    }

    private int verifyBuildShip(Resources res, Ship ship, int amount) {
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

    private void verifyFleetApi(UUID userId, String shipName) {
        if (!verifyDataAccess.ifUserIdExists(userId)) {
            logger.warn("Wrong user id!");
            throw new ApiRequestException("Invalid user ID!");
        }
        if (!EnumUtils.isValidEnum(ShipName.class, shipName)) {
            logger.warn("Wrong Ship name!");
            throw new ApiRequestException(shipName + " does not exists");
        }
    }
    private void verifyFleetApi(UUID userId) {
        if (!verifyDataAccess.ifUserIdExists(userId)) {
            logger.warn("Wrong user id!");
            throw new ApiRequestException("Invalid user ID!");
        }
    }
}