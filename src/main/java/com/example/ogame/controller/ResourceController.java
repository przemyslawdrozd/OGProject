package com.example.ogame.controller;

import com.example.ogame.model.Resources;
import com.example.ogame.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource-api")
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        logger.info("Init - resourceService");
        this.resourceService = resourceService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/resources")
    public Resources getResourcesByUserId(@PathVariable("user_id") String userID) {
        logger.info("GET resources - " + userID);
        return resourceService.getResources(userID);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{user_id}/addextra")
    public int addExtraResources(@PathVariable("user_id") String userID) {
        logger.info("PUT Extra resources - " + userID);
        return resourceService.addExtraResources(userID);
    }
}
