package com.example.ogame.services;

import com.example.ogame.datasource.FleetDataAccess;
import com.example.ogame.models.fleet.Ship;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// TODO 2 Fleet service to retrieve data from api and send it to Database
@Service
public class FleetService {

    private final FleetDataAccess fleetDataAccess;

    public FleetService(FleetDataAccess fleetDataAccess) {
        this.fleetDataAccess = fleetDataAccess;
    }

    // TODO 8 Method that will return all ships
    public List<Ship> getFleet(UUID userId) {
        // Get User fleet_id from instance
//        fleetDataAccess.selectFleetId(userId);
        // TODO 9* Fleet instance has list of all ships, maybe create some ships

//        fleetDataAccess.SelectFleet(fleetId);
        return null;
    }
}
