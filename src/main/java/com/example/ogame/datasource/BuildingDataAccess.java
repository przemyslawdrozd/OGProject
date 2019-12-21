package com.example.ogame.datasource;

import com.example.ogame.models.building.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class BuildingDataAccess {

    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(BuildingDataAccess.class);

    @Autowired
    public BuildingDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Building> selectBuildings(UUID user_id) {
        // TODO In future Try to make shorter query - from user_id retrieve buildings_id
        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium FROM building " +
                "INNER JOIN buildings ON building_id = b_metal_id OR building_id = b_cristal_id OR building_id = b_deuterium_id " +
                "JOIN user_instance USING (buildings_id)" +
                "WHERE user_instance.user_id = ?";
        logger.info("SELECT LIST OF BUILDINGS SQL = " + sql);

        return jdbcTemplate.query(
                sql,
                new Object[]{user_id},
                getBuildingRowMapper()
        );
    }

    public Building insertMetalBuilding(UUID user_id) {
        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium FROM building " +
                "INNER JOIN buildings ON building_id = b_metal_id " +
                "JOIN user_instance USING (buildings_id)" +
                "WHERE user_instance.user_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {user_id},
                getBuildingRowMapper()
        );
    }

    // Get Single building
    private RowMapper<Building> getBuildingRowMapper() {
        return (resultSet, i) -> {
            UUID building_id = UUID.fromString(resultSet.getString("building_id"));
            String name = resultSet.getString("namee");
            int lvl = resultSet.getInt("lvl");
            int needed_metal = resultSet.getInt("needed_metal");
            int needed_cristal = resultSet.getInt("needed_cristal");
            int needed_deuterium = resultSet.getInt("needed_deuterium");

            return new Building(building_id, name, lvl, needed_metal, needed_cristal, needed_deuterium);
        };
    }

    public Building insertCristalBuilding(UUID user_id) {
        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium FROM building " +
                "INNER JOIN buildings ON building_id = b_cristal_id " +
                "JOIN user_instance USING (buildings_id)" +
                "WHERE user_instance.user_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {user_id},
                getBuildingRowMapper()
        );
    }

    public Building insertDeuteriumBuilding(UUID user_id) {
        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium FROM building " +
                "INNER JOIN buildings ON building_id = b_deuterium_id " +
                "JOIN user_instance USING (buildings_id)" +
                "WHERE user_instance.user_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {user_id},
                getBuildingRowMapper()
        );
    }

    public int updateBuilding(Building building) {
        final String sql = "UPDATE building " +
                "SET lvl = ?, needed_metal = ?, needed_cristal = ?, needed_deuterium = ?" +
                "WHERE building_id = ?";
        logger.info("UPDATE buildingLvlUp SQL = " + sql);
        return jdbcTemplate.update(
                sql,
                building.getLevel(),
                building.getNeededMetal(),
                building.getNeededCristal(),
                building.getNeededDeuterium(),
                building.getBuilding_id());
    }
}
