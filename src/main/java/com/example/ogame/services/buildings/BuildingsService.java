package com.example.ogame.services.buildings;

import com.example.ogame.datasource.BuildingDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
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

    @Autowired
    public BuildingsService(BuildingDataAccess buildingDataAccess) {
        this.buildingDataAccess = buildingDataAccess;
    }

    public List<? extends Building> getBuildings(String userID) {
        logger.info("getBuildings from " + userID);
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.selectBuildings(user_id);
    }

    public Building getMetalBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.selectMetalBuilding(user_id);
    }

    public Building getCristalBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.selectCristalBuilding(user_id);
    }

    public Building getDeuteriumBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.selectDeuteriumBuilding(user_id);
    }
}
