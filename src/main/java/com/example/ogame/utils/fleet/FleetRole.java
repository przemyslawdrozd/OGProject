package com.example.ogame.utils.fleet;

import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.Resources;
import com.example.ogame.models.fleet.Ship;
import com.example.ogame.services.ResourceService;
import com.example.ogame.utils.facilities.ShipName;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FleetRole {

    private final ResourceService resourceService;
    private final VerifyDataAccess verifyDataAccess;
    private final Logger logger = LoggerFactory.getLogger(FleetRole.class);

    public FleetRole(ResourceService resourceService,
                     VerifyDataAccess verifyDataAccess) {
        this.resourceService = resourceService;
        this.verifyDataAccess = verifyDataAccess;
    }

    public int buildShip(Resources res, Ship ship, int amount) {
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

    public void verifyFleetApi(UUID userId, String shipName) {
        if (!verifyDataAccess.ifUserIdExists(userId)) {
            logger.warn("Wrong user id!");
            throw new ApiRequestException("Invalid user ID!");
        }
        if (!EnumUtils.isValidEnum(ShipName.class, shipName)) {
            logger.warn("Wrong Ship name!");
            throw new ApiRequestException(shipName + " does not exists");
        }
    }

    public void verifyFleetApi(UUID userId) {
        if (!verifyDataAccess.ifUserIdExists(userId)) {
            logger.warn("Wrong user id!");
            throw new ApiRequestException("Invalid user ID!");
        }
    }
}