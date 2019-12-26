package com.example.ogame.services;

import com.example.ogame.datasource.BuildingDataAccess;
import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.models.Resources;
import com.example.ogame.models.building.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuildingsService {

    private Logger logger = LoggerFactory.getLogger(BuildingsService.class);
    private final BuildingDataAccess buildingDataAccess;
    private final ResourceDataAccess resourceDataAccess;

    @Autowired
    public BuildingsService(BuildingDataAccess buildingDataAccess,
                            ResourceDataAccess resourceDataAccess) {
        this.buildingDataAccess = buildingDataAccess;
        this.resourceDataAccess = resourceDataAccess;
    }

    public List<? extends Building> getBuildings(String userID) {
        logger.info("getBuildings from " + userID);
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.selectListOfBuildings(user_id);
    }

    public Building getBuildingByName(String userID, String b_name) {
        UUID user_id = UUID.fromString(userID);
         return buildingDataAccess.selectBuildingByUserIdAndBuildingName(user_id, b_name);
    }

    public boolean lvlUpMetal(String userID, String b_name) {
        UUID user_id = UUID.fromString(userID);
        Building building = buildingDataAccess.selectBuildingByUserIdAndBuildingName(user_id, b_name);
        Resources resources =  resourceDataAccess.selectResourcesByUserId(user_id);

        if (building.getNeededMetal() < resources.getMetal() &&
                building.getNeededCristal() < resources.getCristal()) {

            resources.setMetal(resources.getMetal() - building.getNeededMetal());
            resources.setCristal(resources.getCristal() - building.getNeededCristal());

            building.lvlUpBuilding();
            logger.info(building.getName() + " has been lvl up : " + building.getLevel());

            resourceDataAccess.updateResources(new Resources(
                    resources.getResource_id(),
                    resources.getMetal(),
                    resources.getCristal(),
                    resources.getDeuterium()));

            buildingDataAccess.updateBuilding(building);
            return true;
        }
        return false;
    }

}
