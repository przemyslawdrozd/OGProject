package com.example.ogame.models.fleet;

import com.example.ogame.utils.FleetHelper;
import com.example.ogame.utils.ShipName;

import java.util.*;

// TODO 12 Fleet Class to help manage Ships
public class Fleet {

    private final UUID fleetId;
    private Map<ShipName, UUID> shipsIdMap;

    public Fleet(UUID fleetId, List<Ship> ships) {
        this.fleetId = fleetId;
        this.shipsIdMap = new TreeMap<>();
        this.shipsIdMap.put(ShipName.FLEET_ID, fleetId);
        initShipsIdMap(ships);
    }

    // TODO 13 Mapped Fleet to retrieve ShipName and ShipId
    private void initShipsIdMap(List<Ship> ships) {
        ships.forEach(ship -> shipsIdMap.put(ship.getName(), ship.getShipId()));
    }

    public UUID getFleetId() {
        return fleetId;
    }

    public Map<ShipName, UUID> getShipList() {
        return shipsIdMap;
    }
}