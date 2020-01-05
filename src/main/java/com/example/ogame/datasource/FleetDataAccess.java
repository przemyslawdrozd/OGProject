package com.example.ogame.datasource;

import com.example.ogame.models.fleet.Fleet;
import com.example.ogame.models.fleet.Ship;
import com.example.ogame.utils.FleetHelper;
import com.example.ogame.utils.ShipName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class FleetDataAccess {

    private final JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(FleetDataAccess.class);

    public FleetDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertFleet(UUID fleet_id) {
        List<Ship> ships = FleetHelper.createShips();
        Fleet fleet = new Fleet(fleet_id, ships);
        ships.forEach(this::insertNewShip);
        logger.info("Ships inserted");

        final String sql = "INSERT INTO fleet (" +
                "fleet_id, small_cargo_ship, large_cargo_ship, light_fighter, battle_ship, colony_ship)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, FleetHelper.insertFleet(fleet.getShipList()));
        logger.info("Fleet inserted");
    }

    private void insertNewShip(Ship ship) {
        final String sql = "INSERT INTO ship (" +
                "ship_id, ship_name, attack, defense, speed, capacity, fuel, metal_cost, cristal_cost, deuterium_cost, amount_of_ship)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insertNewShip - " + sql);
        jdbcTemplate.update(sql, FleetHelper.insertShip(ship));
    }

    public List<Ship> selectFleet(UUID userId) {
        List<Ship> ships = new ArrayList<>();
        List<UUID> fleetIds = getFleetIds(getFleetId(userId));
        fleetIds.forEach(ship_id -> ships.add(selectShipById(ship_id)));
        return ships;
    }

    private Ship selectShipById(UUID ship_id) {
        final String sql = "SELECT * FROM ship WHERE ship_id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {ship_id},
                (rs, i) -> new Ship(
                        Enum.valueOf(ShipName.class, rs.getString("ship_name")),
                        rs.getInt("attack"),
                        rs.getInt("defense"),
                        rs.getInt("speed"),
                        rs.getInt("capacity"),
                        rs.getInt("fuel"),
                        rs.getInt("metal_cost"),
                        rs.getInt("cristal_cost"),
                        rs.getInt("deuterium_cost"),
                        rs.getInt("amount_of_ship")
                ));
    }

    // TODO Change It!!!!
    private List<UUID> getFleetIds(UUID fleet_id) {
        final String sql = "SELECT * FROM fleet WHERE fleet_id = ?";
        List<UUID> shipsIdList = new ArrayList<>();
        jdbcTemplate.queryForObject(
                sql,
                new Object[]{fleet_id},
                (rs, i) -> {
                    shipsIdList.add(UUID.fromString(rs.getString("small_cargo_ship")));
                    shipsIdList.add(UUID.fromString(rs.getString("large_cargo_ship")));
                    shipsIdList.add(UUID.fromString(rs.getString("light_fighter")));
                    shipsIdList.add(UUID.fromString(rs.getString("battle_ship")));
                    shipsIdList.add(UUID.fromString(rs.getString("colony_ship")));
                    return null;
                });
        return shipsIdList;
    }

    private UUID getFleetId(UUID userId) {
        final String sql = "SELECT fleet_id FROM user_instance WHERE user_id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {userId},
                (rs, i) -> UUID.fromString(rs.getString("fleet_id")));
    }

    public Ship selectShip(UUID userID, String shipName) {
        UUID fleetId = getFleetId(userID);
        final String sql = "SELECT " + shipName + " FROM fleet where fleet_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{fleetId},
                (resultSet, i) -> selectShipById(UUID.fromString(resultSet.getString(shipName))));
    }
}