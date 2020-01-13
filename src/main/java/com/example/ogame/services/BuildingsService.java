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
            building.getNeededDeuterium() < resources.getDeuterium() &&
            building.getIsAbleToBuild() == 1) {

            blockRestOfFacilities(userId);

            resources.utilizeForBuild(
                    building.getNeededMetal(),
                    building.getNeededCristal(),
                    building.getNeededDeuterium()
            );

            resourceDataAccess.updateResources(resources);
            facilitiesDataAccess.isBuilding(building.getBuildingId());
            logger.info(building.getName() +  " able to building: 2");
            return true;
        }
        throw new ApiRequestException("Not enough resources or is building!");
    }

    private void blockRestOfFacilities(UUID userId) {
        List<Building> technologies = facilitiesDataAccess.selectFacilities(userId);
        technologies.forEach(b -> facilitiesDataAccess.blockBuilding(b.getBuildingId()));
        logger.info("facilities are blocked: 0");
    }

}
