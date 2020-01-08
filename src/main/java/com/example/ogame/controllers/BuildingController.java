package com.example.ogame.controllers;

import com.example.ogame.models.facilities.Building;
import com.example.ogame.services.BuildingsService;
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

    @GetMapping("/{user_id}/buildings")
    public List<? extends Building> getListOfBuildings(@PathVariable("user_id") String userID) {
        logger.info("GET listOfBuildings - " + userID);
        return buildingsService.getBuildings(userID);
    }

    @GetMapping("/{user_id}/building/{building_name}")
    public Building getBuilding(@PathVariable("user_id") String userID,
                                @PathVariable("building_name") String b_name) {
        return buildingsService.getBuildingByName(userID, b_name);
    }

    @PutMapping("/{user_id}/building/{building_name}")
    public Boolean lvlUpMetal(@PathVariable("user_id") String userID,
                              @PathVariable("building_name") String b_name) {
        logger.info("PUT " + b_name + " lvl up - " + userID);
        return buildingsService.lvlUpMetal(userID, b_name);
    }
}
