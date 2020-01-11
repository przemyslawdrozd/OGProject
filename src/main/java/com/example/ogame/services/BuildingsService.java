package com.example.ogame.services;

import com.example.ogame.datasource.FacilitiesDataAccess;
import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.Resources;
import com.example.ogame.models.facilities.Building;
import com.example.ogame.utils.facilities.FacilitiesRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuildingsService {
    private Logger logger = LoggerFactory.getLogger(BuildingsService.class);
    private final FacilitiesDataAccess facilitiesDataAccess;
    private final ResourceDataAccess resourceDataAccess;
    private final FacilitiesRule facilitiesRule;

    @Autowired
    public BuildingsService(FacilitiesDataAccess facilitiesDataAccess,
                            ResourceDataAccess resourceDataAccess,
                            FacilitiesRule facilitiesRule) {
        this.facilitiesDataAccess = facilitiesDataAccess;
        this.resourceDataAccess = resourceDataAccess;
        this.facilitiesRule = facilitiesRule;
    }

    public List<Building> getBuildings(UUID userId) {
        logger.info("getBuildings from " + userId);
        facilitiesRule.verifyUser(userId);
        return facilitiesDataAccess.selectFacilities(userId);
    }

    public Building getBuildingByName(UUID userId, String buildingName) {
        facilitiesRule.verifyFacilitiesApi(userId, buildingName);
        return facilitiesDataAccess.selectBuilding(userId, buildingName);
    }

    public boolean lvlUpBuilding(UUID userId, String buildingName) {
        facilitiesRule.verifyFacilitiesApi(userId, buildingName);

        Building building = facilitiesDataAccess.selectBuilding(userId, buildingName);
        Resources resources =  resourceDataAccess.selectResources(userId);

        if (building.getNeededMetal() < resources.getMetal() &&
                building.getNeededCristal() < resources.getCristal() &&
                building.getNeededDeuterium() < resources.getDeuterium()) {

            resources.utilizeForBuild(
                    building.getNeededMetal(),
                    building.getNeededCristal(),
                    building.getNeededDeuterium()
            );

            building.lvlUpBuilding();
            logger.info(building.getName() + " has been lvl up : " + building.getLevel());

            resourceDataAccess.updateResources(new Resources(
                    resources.getResourceId(),
                    resources.getMetal(),
                    resources.getCristal(),
                    resources.getDeuterium()));

            facilitiesDataAccess.updateBuilding(building);
            return true;
        }
        throw new ApiRequestException("Not enough resources!");
    }

}
