package com.example.ogame.datasource;

import com.example.ogame.models.building.Building;
import com.example.ogame.models.building.ResourceBuilding;
import com.example.ogame.utils.BuildingsHelper;
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

    // TODO This method retrieve only resource buildings
    public List<? extends Building> selectBuildings(UUID user_id) {
        // TODO In future Try to make shorter query - from user_id retrieve buildings_id
        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium, build_time, production_per_hour FROM building " +
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

    public Building selectMetalBuilding(UUID user_id) {
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

    public Building selectCristalBuilding(UUID user_id) {
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

    public Building selectDeuteriumBuilding(UUID user_id) {
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

    // TODO use only one method to retrieve every building by id
    public Building selectBuildingById(UUID building_id) {
        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium, build_time, production_per_hour " +
                "FROM building";
        return null;
    }

    private RowMapper<? extends Building> getBuildingRowMapper() {
        return (resultSet, i) -> {
            UUID building_id = UUID.fromString(resultSet.getString("building_id"));
            String name = resultSet.getString("namee");
            int lvl = resultSet.getInt("lvl");
            int needed_metal = resultSet.getInt("needed_metal");
            int needed_cristal = resultSet.getInt("needed_cristal");
            int needed_deuterium = resultSet.getInt("needed_deuterium");
            int buildTime = resultSet.getInt("build_time");
            int production_per_hour;

            try {
                production_per_hour = resultSet.getInt("production_per_hour");
            } catch (Exception e) {
                return new Building(building_id,
                        name, lvl, needed_metal, needed_cristal,
                        needed_deuterium, buildTime);
            }

            return new ResourceBuilding(building_id,
                    name, lvl, needed_metal, needed_cristal,
                    needed_deuterium, buildTime, production_per_hour);
        };
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

    public void insertNewBuildings(UUID buildings_id) {
        List<Building> buildingList = BuildingsHelper.createNewBuildingsForInstance();

        for (Building building: buildingList) {
            if (building instanceof ResourceBuilding) {
                insertNewResourceBuilding((ResourceBuilding) building);
            } else {
                insertNewBuilding(building);
            }
            logger.info(building.getName() + " has been created");
        }

        logger.info("Buildings inserted");

        final String sql = "INSERT INTO buildings (" +
                "buildings_id, b_metal_id, b_cristal_id, b_deuterium_id, b_shipyard_id) VALUES " +
                "(?, ?, ?, ?, ?)";
        logger.info("insertNewBuildings - " + sql);
        jdbcTemplate.update(sql, BuildingsHelper.getBuildingsIds(buildings_id, buildingList));
    }

    public void insertNewBuilding(Building building) {
        final String sql = "INSERT INTO building (" +
                "building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium, build_time) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        logger.info("insertNewBuilding - " + sql);
        jdbcTemplate.update(sql, BuildingsHelper.insertBuilding(building));
    }

    public void insertNewResourceBuilding(ResourceBuilding building) {
        final String sql = "INSERT INTO building (" +
                "building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium, build_time, production_per_hour) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insertNewBuilding - " + sql);
        jdbcTemplate.update(sql, BuildingsHelper.insertResourceBuilding(building));
    }
}
