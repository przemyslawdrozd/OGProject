package com.example.ogame.services.buildings;

import com.example.ogame.datasource.BuildingDataAccess;
import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.models.building.Building;
import com.example.ogame.models.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuildingsLvlUpService {

    private Logger logger = LoggerFactory.getLogger(BuildingsLvlUpService.class);
    private final BuildingDataAccess buildingDataAccess;
    private final ResourceDataAccess resourceDataAccess;

    public BuildingsLvlUpService(BuildingDataAccess buildingDataAccess, ResourceDataAccess resourceDataAccess) {
        this.buildingDataAccess = buildingDataAccess;
        this.resourceDataAccess = resourceDataAccess;
    }

    // lvl up metal if enough resources
    public boolean lvlUpMetal(String userID) {
        UUID user_id = UUID.fromString(userID);

        // Get Metal building from this user
        Building buildingMetal = buildingDataAccess.insertMetalBuilding(user_id);
        Resources resources =  resourceDataAccess.selectResourcesByUserId(user_id);

        if (buildingMetal.getNeededMetal() < resources.getMetal() ||
                buildingMetal.getNeededCristal() < resources.getCristal()) {

            resources.setMetal(resources.getMetal() - buildingMetal.getNeededMetal());
            resources.setCristal(resources.getCristal() - buildingMetal.getNeededCristal());

            buildingMetal.lvlUpBuilding();
            logger.info(buildingMetal.getName() + " has been lvl up : " + buildingMetal.getLevel());

            resourceDataAccess.updateResources(new Resources(
                    resources.getResource_id(),
                    resources.getMetal(),
                    resources.getCristal(),
                    resources.getDeuterium()));

            buildingDataAccess.updateBuilding(buildingMetal);
            return true;
        }
        return false;
    }
}
