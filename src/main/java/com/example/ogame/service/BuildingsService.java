package com.example.ogame.service;

import com.example.ogame.datasource.DataAccessService;
import com.example.ogame.model.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuildingsService {

    private Logger logger = LoggerFactory.getLogger(BuildingsService.class);
    private final DataAccessService dataAccessService;

    public BuildingsService(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    public List<Building> getBuildings(String userID) {
        logger.info("getBuildings from " + userID);
        UUID user_id = UUID.fromString(userID);
        return dataAccessService.selectBuildings(user_id);
    }

    public Building getMetalBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return dataAccessService.insertMetalBuilding(user_id);
    }

    public Building getCristalBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return dataAccessService.insertCristalBuilding(user_id);
    }

    public Building getDeuteriumBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return dataAccessService.insertDeuteriumBuilding(user_id);
    }
}
