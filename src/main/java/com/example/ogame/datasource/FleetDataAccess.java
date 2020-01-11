package com.example.ogame.datasource;

import com.example.ogame.models.fleet.Fleet;
import com.example.ogame.models.fleet.Ship;
import com.example.ogame.utils.fleet.FleetHelper;
import com.example.ogame.utils.fleet.ShipName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FleetDataAccess {

    private final JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(FleetDataAccess.class);

    public FleetDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ship> selectFleet(UUID userId) {
        return getShipIds(getFleetId(userId))
                .stream()
                .map(this::selectShipById)
                .collect(Collectors.toList());
    }

    public Ship selectShipById(UUID ship_id) {
        final String sql = "SELECT * FROM ship WHERE ship_id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {ship_id},
                getShipRowMapper()
        );
    }

    private RowMapper<Ship> getShipRowMapper() {
        return (rs, i) -> new Ship(
                UUID.fromString(rs.getString("ship_id")),
                Enum.valueOf(ShipName.class, rs.getString("ship_name")),
                rs.getInt("attack"),
                rs.getInt("defense"),
                rs.getInt("speed"),
                rs.getInt("capacity"),
                rs.getInt("fuel"),
                rs.getInt("metal_cost"),
                rs.getInt("cristal_cost"),
                rs.getInt("deuterium_cost"),
                rs.getInt("amount_of_ship"));
    }

    private List<UUID> getShipIds(UUID fleet_id) {
        final String sql = "SELECT * FROM fleet WHERE fleet_id = ?";
        return jdbcTemplate.queryForList(sql, fleet_id)
                .get(0).values()
                .stream().skip(1)
                .map(id -> (UUID) id)
                .collect(Collectors.toList());
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
                (rs, i) -> selectShipById(UUID.fromString(rs.getString(shipName))));
    }

    public int updateShip(Ship ship) {
        final String sql = "UPDATE ship SET amount_of_ship = ? WHERE ship_id = ?";
        return jdbcTemplate.update(
                sql,
                ship.getAmountOfShip(),
                ship.getShipId()
        );
    }
}