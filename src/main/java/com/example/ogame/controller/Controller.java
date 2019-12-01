package com.example.ogame.controller;

import com.example.ogame.model.*;
import com.example.ogame.service.BuildingsService;
import com.example.ogame.service.ResourceService;
import com.example.ogame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    private Logger logger = LoggerFactory.getLogger(Controller.class);

    private final ResourceService resourceService;
    private final BuildingsService buildingsService;

    @Autowired
    public Controller(UserService userService,
                      ResourceService resourceService,
                      BuildingsService buildingsService) {
        this.resourceService = resourceService;
        this.buildingsService = buildingsService;
    }






    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/resources")
    public Resources getResourcesByUserId(@PathVariable("user_id") String userID) {
        return resourceService.getResources(userID);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{user_id}/addextra")
    public int addExtraResources(@PathVariable("user_id") String userID) {
        return resourceService.addExtraResources(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/buildings")
    public List<Building> getListOfBuildings(@PathVariable("user_id") String userID) {
        return buildingsService.getBuildings(userID);
    }

    // TODO GET single building
    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/metal")
    public Building getMetalBuilding(@PathVariable("user_id") String userID) {
        return buildingsService.getMetalBuilding(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/cristal")
    public Building getCristalBuilding(@PathVariable("user_id") String userID) {
        return buildingsService.getCristalBuilding(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/deuterium")
    public Building getDeuteriumBuilding(@PathVariable("user_id") String userID) {
        return buildingsService.getDeuteriumBuilding(userID);
    }
}















