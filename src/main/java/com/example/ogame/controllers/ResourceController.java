package com.example.ogame.controllers;

import com.example.ogame.models.Resources;
import com.example.ogame.services.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource-api")
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        logger.info("Init - resourceService");
        this.resourceService = resourceService;
    }

    @GetMapping(path = "/{user_id}/resources")
    public Resources getResourcesByUserId(@PathVariable("user_id") String userID) {
        logger.info("GET resources - " + userID);
        return resourceService.getResources(userID);
    }

    @GetMapping(path = "/{user_id}/addextra")
    public int addExtraResources(@PathVariable("user_id") String userID) {
        logger.info("PUT Extra resources - " + userID);
        return resourceService.addExtraResources(userID);
    }
}
