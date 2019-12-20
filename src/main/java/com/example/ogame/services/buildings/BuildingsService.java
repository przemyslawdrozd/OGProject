package com.example.ogame.services.buildings;

import com.example.ogame.datasource.BuildingDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.models.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuildingsService {

    private Logger logger = LoggerFactory.getLogger(BuildingsService.class);
    private final VerifyDataAccess verifyDataAccess;
    private final BuildingDataAccess buildingDataAccess;

    @Autowired
    public BuildingsService(VerifyDataAccess verifyDataAccess, BuildingDataAccess buildingDataAccess) {
        this.verifyDataAccess = verifyDataAccess;
        this.buildingDataAccess = buildingDataAccess;
    }

    public List<Building> getBuildings(String userID) {
        logger.info("getBuildings from " + userID);
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.selectBuildings(user_id);
    }

    public Building getMetalBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.insertMetalBuilding(user_id);
    }

    public Building getCristalBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.insertCristalBuilding(user_id);
    }

    public Building getDeuteriumBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return buildingDataAccess.insertDeuteriumBuilding(user_id);
    }
}
