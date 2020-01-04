package com.example.ogame.utils;

import com.example.ogame.models.fleet.Ship;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.ogame.utils.ShipName.*;

// TODO 10 Fleet Helper to create and manage Ships
public class FleetHelper {

    // TODO 10.1 List<Ship> that will return Ships
    public static List<Ship> createShips() {

       return List.of(
               new Ship(UUID.randomUUID(), SMALL_CARGO_SHIP, 5, 10, 200, 5000, 10,
                       2000, 2000, 0, 0),

               new Ship(UUID.randomUUID(), LARGE_CARGO_SHIP, 10, 20, 100, 25000, 30,
                       6000, 6000, 0, 0),

               new Ship(UUID.randomUUID(), LIGHT_FIGHTER, 50, 15, 200, 50, 10,
                       3000, 1000, 0, 0),

               new Ship(UUID.randomUUID(), BATTLE_SHIP, 700, 200, 100, 2000, 100,
                       45000, 15000, 0, 0),

               new Ship(UUID.randomUUID(), COLONY_SHIP, 10, 30, 80, 5000, 40,
                       10000, 20000, 10000, 0)
               );
    }

    // TODO 15.3 get object array from ship
    public static Object[] insertShip(Ship ship) {
        return new Object[] {
                ship.getShipId(),
                ship.getName().toString(),
                ship.getAttack(),
                ship.getDefense(),
                ship.getSpeed(),
                ship.getCapacity(),
                ship.getFuel(),
                ship.getMetalCost(),
                ship.getCristalCost(),
                ship.getDeuteriumCost(),
                ship.getAmountOfShip()
        };
    }

    // TODO 16.1 get object array of UUIDs from Map
    public static Object[] insertFleet(Map<ShipName, UUID> shipMap) {
        return shipMap.values().toArray();
    }
}