package com.example.ogame.controllers;

import com.example.ogame.models.fleet.Ship;
import com.example.ogame.services.FleetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fleet-api")
public class FleetController {

    private final FleetService fleetService;
    private Logger logger = LoggerFactory.getLogger(FleetController.class);

    public FleetController(FleetService fleetService) {
        this.fleetService = fleetService;
    }

    @GetMapping("/{id}")
    public List<Ship> GetFleet(@PathVariable("id") String id) {
        logger.info("GET fleet - " + id);
        UUID userId = UUID.fromString(id);
        return fleetService.getFleet(userId);
    }

    @GetMapping("/{id}/{shipName}")
    public Ship getShip(@PathVariable("id") String id,
                        @PathVariable("shipName") String shipName) {
        UUID userId = UUID.fromString(id);
        logger.info("GET ship - " + shipName);
        return fleetService.getShipByName(userId, shipName);
    }

    @PutMapping("/{id}/{shipName}/{amount}")
    public int buildShip(@PathVariable("id") String id,
                          @PathVariable("shipName") String shipName,
                          @PathVariable("amount") int amount) {
        UUID userId = UUID.fromString(id);
        logger.info("PUT - try to build " + amount + " " + shipName);
        return fleetService.buildShip(userId, shipName, amount);
    }
}