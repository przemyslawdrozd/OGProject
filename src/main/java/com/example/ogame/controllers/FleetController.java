package com.example.ogame.controllers;

import com.example.ogame.models.fleet.Ship;
import com.example.ogame.services.FleetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

// TODO 1 Fleet Controller Api
@RestController
@RequestMapping("/fleet-api")
public class FleetController {

    // TODO 6 Inject FleetService
    private final FleetService fleetService;
    private Logger logger = LoggerFactory.getLogger(FleetController.class);

    public FleetController(FleetService fleetService) {
        this.fleetService = fleetService;
    }

    // TODO 7 Create All Apis` endpoints
    // TODO 7.1* Thinking... Fleet Class or List<Fleet>
    // TODO 7.2 @GetMapping and @PathVariable
    @GetMapping("/{user_id}")
    public List<Ship> GetAllFleet(@PathVariable("user_id") String user_id) {
        logger.info("GET fleet - " + user_id);
        UUID userId = UUID.fromString(user_id);
        return fleetService.getFleet(userId);
    }
}
