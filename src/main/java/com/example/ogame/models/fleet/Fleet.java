package com.example.ogame.models.fleet;

import java.util.*;

public class Fleet {

    private List<UUID> shipsIdList;

    public Fleet(UUID fleetId, List<Ship> ships) {
        this.shipsIdList = new ArrayList<>();
        this.shipsIdList.add(fleetId);
        initShipsIdList(ships);
    }

    private void initShipsIdList(List<Ship> ships) {
        ships.forEach(ship -> shipsIdList.add(ship.getShipId()));
    }

    public List<UUID> getShipList() {
        return shipsIdList;
    }
}