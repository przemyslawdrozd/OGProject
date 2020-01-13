package com.example.ogame.controllers;

import com.example.ogame.models.facilities.Building;
import com.example.ogame.services.BuildingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public List<Building> getListOfBuildings(@PathVariable("id") String id) {
        logger.info("GET listOfBuildings - " + id);
        UUID userId = UUID.fromString(id);
        return buildingsService.getBuildings(userId);
    }

    @GetMapping("/{id}/{buildingName}")
    public Building getBuilding(@PathVariable("id") String id,
                                @PathVariable("buildingName") String buildingName) {
        UUID userId = UUID.fromString(id);
        return buildingsService.getBuildingByName(userId, buildingName);
    }

    @PutMapping("/{id}/{buildingName}")
    public Boolean lvlUpBuilding(@PathVariable("id") String id,
                                 @PathVariable("buildingName") String buildingName) {
        logger.info("PUT " + buildingName + " lvl up - " + id);
        UUID userId = UUID.fromString(id);
        return buildingsService.lvlUpBuilding(userId, buildingName);
    }
}
