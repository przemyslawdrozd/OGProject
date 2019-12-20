package com.example.ogame.controllers.buildings;

import com.example.ogame.services.buildings.BuildingsLvlUpService;
import com.example.ogame.services.buildings.BuildingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/building-api/lvl-up")
public class BuildingsLvlUpController {

    private Logger logger = LoggerFactory.getLogger(BuildingsLvlUpController.class);
    private final BuildingsLvlUpService buildingsLvlUpService;

    @Autowired
    public BuildingsLvlUpController(BuildingsLvlUpService buildingsLvlUpService) {
        logger.info("Init - buildingService");
        this.buildingsLvlUpService = buildingsLvlUpService;
    }

    @GetMapping(path = "/{user_id}/building/metal")
    public Boolean lvlUpMetal(@PathVariable("user_id") String userID) {
        logger.info("GET Metal lvl up - " + userID);
        return buildingsLvlUpService.lvlUpMetal(userID);
    }
}
