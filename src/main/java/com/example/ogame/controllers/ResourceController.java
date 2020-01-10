package com.example.ogame.controllers;

import com.example.ogame.models.Resources;
import com.example.ogame.services.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/resource-api")
public class ResourceController {
    private Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        logger.info("Init - resourceService");
        this.resourceService = resourceService;
    }

    @GetMapping(path = "/{id}")
    public Resources getResourcesByUserId(@PathVariable("id") String id) {
        logger.info("GET resources - " + id);
        UUID userID = UUID.fromString(id);
        return resourceService.getResources(userID);
    }

    @PutMapping(path = "/{id}")
    public int addExtraResources(@PathVariable("id") String id) {
        logger.info("PUT Extra resources - " + id);
        UUID userId = UUID.fromString(id);
        return resourceService.addExtraResources(userId);
    }
}
