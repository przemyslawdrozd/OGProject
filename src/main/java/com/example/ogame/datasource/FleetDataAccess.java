package com.example.ogame.datasource;

import com.example.ogame.models.fleet.Fleet;
import com.example.ogame.models.fleet.Ship;
import com.example.ogame.utils.FleetHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

// TODO 3 Repository of fleet to put data in Database
@Repository
public class FleetDataAccess {

    private final JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(FleetDataAccess.class);

    public FleetDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TODO 15 insertFleet
    public void insertFleet(UUID fleet_id) {
        // TODO 15.1 Get created ships
        List<Ship> ships = FleetHelper.createShips();
        Fleet fleet = new Fleet(fleet_id, ships);

        // TODO 15.1 insertShips for each ship in list
        ships.forEach(this::insertNewShip);
        logger.info("Ships inserted");

        // TODO 16 insert fleet
        final String sql = "INSERT INTO fleet (" +
                "fleet_id, small_cargo_sheep_id, large_cargo_sheep_id, light_fighter_id, battle_ship_id, colony_ship_id)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, FleetHelper.insertFleet(fleet.getShipList()));
        logger.info("Fleet inserted");
    }

    // TODO 15.2 insertShips
    private void insertNewShip(Ship ship) {
        final String sql = "INSERT INTO ship (" +
                "ship_id, ship_name, attack, defense, speed, capacity, fuel, metal_cost, cristal_cost, deuterium_cost, amount_of_ship)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insertNewShip - " + sql);
        jdbcTemplate.update(sql, FleetHelper.insertShip(ship));
    }
}