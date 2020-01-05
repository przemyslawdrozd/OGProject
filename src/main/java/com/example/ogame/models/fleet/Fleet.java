package com.example.ogame.models.fleet;

import java.util.*;

public class Fleet {

    private List<UUID> shipsIdList;

    public Fleet(UUID fleetId, List<Ship> ships) {
        this.shipsIdList = new ArrayList<>();
        this.shipsIdList.add(fleetId);
        initShipsIdMap(ships);
    }

    private void initShipsIdMap(List<Ship> ships) {
        ships.forEach(ship -> shipsIdList.add(ship.getShipId()));
    }

    public List<UUID> getShipList() {
        return shipsIdList;
    }
}