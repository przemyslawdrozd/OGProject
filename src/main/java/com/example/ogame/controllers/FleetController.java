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

    @GetMapping("/{user_id}")
    public List<Ship> GetAllFleet(@PathVariable("user_id") String user_id) {
        logger.info("GET fleet - " + user_id);
        UUID userId = UUID.fromString(user_id);
        return fleetService.getFleet(userId);
    }

    @GetMapping("/{user_id}/{ship_name}")
    public Ship getShip(@PathVariable("user_id") String user_id,
                        @PathVariable("ship_name") String ship_name) {
        UUID userID = UUID.fromString(user_id);
        return fleetService.getShipByName(userID, ship_name);
    }

    @PutMapping("/{user_id}/{ship_name}/{amount}")
    public void buildShip(@PathVariable("user_id") String user_id,
                          @PathVariable("ship_name") String ship_name,
                          @PathVariable("amount") int amount) {
        UUID userID = UUID.fromString(user_id);
        fleetService.buildShip(userID, ship_name, amount);
    }
}