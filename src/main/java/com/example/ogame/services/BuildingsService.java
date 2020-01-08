package com.example.ogame.services;

import com.example.ogame.datasource.FacilitiesDataAccess;
import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.models.Resources;
import com.example.ogame.models.facilities.Building;
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

    @Autowired
    public BuildingsService(FacilitiesDataAccess facilitiesDataAccess,
                            ResourceDataAccess resourceDataAccess) {
        this.facilitiesDataAccess = facilitiesDataAccess;
        this.resourceDataAccess = resourceDataAccess;
    }

    public List<Building> getBuildings(UUID userId) {
        logger.info("getBuildings from " + userId);
        return facilitiesDataAccess.selectFacilities(userId);
    }

    public Building getBuildingByName(UUID userId, String buildingName) {

        return facilitiesDataAccess.selectBuilding(userId, buildingName);
    }

    public boolean lvlUpBuilding(UUID userId, String buildingName) {
        Building building = facilitiesDataAccess.selectBuilding(userId, buildingName);
        Resources resources =  resourceDataAccess.selectResourcesByUserId(userId);

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

            facilitiesDataAccess.updateBuilding(building);
            return true;
        }
        return false;
    }

}
