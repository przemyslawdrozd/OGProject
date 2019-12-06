package com.example.ogame.controller;

import com.example.ogame.model.Building;
import com.example.ogame.service.BuildingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/building-api")
public class BuildingController {

    private Logger logger = LoggerFactory.getLogger(BuildingController.class);

    private final BuildingsService buildingsService;

    @Autowired
    public BuildingController(BuildingsService buildingsService) {
        logger.info("Init - buildingService");
        this.buildingsService = buildingsService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/buildings")
    public List<Building> getListOfBuildings(@PathVariable("user_id") String userID) {
        logger.info("GET listOfBuildings - " + userID);
        return buildingsService.getBuildings(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/metal")
    public Building getMetalBuilding(@PathVariable("user_id") String userID) {
        logger.info("GET Metal building - " + userID);
        return buildingsService.getMetalBuilding(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/cristal")
    public Building getCristalBuilding(@PathVariable("user_id") String userID) {
        logger.info("GET Cristal building - " + userID);
        return buildingsService.getCristalBuilding(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/deuterium")
    public Building getDeuteriumBuilding(@PathVariable("user_id") String userID) {
        logger.info("GET Deuterium building - " + userID);
        return buildingsService.getDeuteriumBuilding(userID);
    }
}