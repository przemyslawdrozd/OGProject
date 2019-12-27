package com.example.ogame.datasource;

import com.example.ogame.models.building.Building;
import com.example.ogame.models.building.BuildingInstance;
import com.example.ogame.models.building.ResourceBuilding;
import com.example.ogame.utils.BuildingsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<? extends Building> selectListOfBuildings(UUID user_id) {
        List<Building> buildingList = new ArrayList<>();
        BuildingInstance buildingInstance = selectBuildingsObjectByBuildingsId(user_id);

        for (UUID uuid: buildingInstance.getUuidList()){
            buildingList.add(selectBuildingByBuildingId(uuid));
        }

        return buildingList;
    }

    public Building selectBuildingByUserIdAndBuildingName(UUID user_id, String buildingName) {
        BuildingInstance buildingInstance = selectBuildingsObjectByBuildingsId(user_id);
        UUID building_id = buildingInstance.getBuildingIdByName(buildingName);

        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium, build_time, production_per_hour" +
                " FROM building WHERE building_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{building_id},
                getBuildingRowMapper()
        );
    }

    public Building selectBuildingByBuildingId(UUID building_id) {
        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium, build_time, production_per_hour" +
                " FROM building WHERE building_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{building_id},
                getBuildingRowMapper()
        );

    }

    public UUID selectBuildingsIdByUserId(UUID user_id) {
        final String sql = "SELECT buildings_id FROM user_instance WHERE user_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{user_id},
                (rs, i) -> UUID.fromString(rs.getString("buildings_id")));
    }

    public BuildingInstance selectBuildingsObjectByBuildingsId(UUID user_id) {
        final UUID buildings_id = selectBuildingsIdByUserId(user_id);
        final String sql = "SELECT b_metal_id, b_cristal_id, b_deuterium_id, b_shipyard_id " +
                "FROM building_instance WHERE buildings_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{buildings_id},
                (rs, i) -> new BuildingInstance(
                        buildings_id,
                        UUID.fromString(rs.getString("b_metal_id")),
                        UUID.fromString(rs.getString("b_cristal_id")),
                        UUID.fromString(rs.getString("b_deuterium_id")),
                        UUID.fromString(rs.getString("b_shipyard_id")))
        );
    }

    public Building selectMetalBuilding(UUID user_id) {
        final String sql = "SELECT building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium FROM building " +
                "INNER JOIN building_instance ON building_id = b_metal_id " +
                "JOIN user_instance USING (buildings_id)" +
                "WHERE user_instance.user_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{user_id},
                getBuildingRowMapper()
        );
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
            int production_per_hour = resultSet.getInt("production_per_hour");

            if (production_per_hour == 0)
                return new Building(building_id, name, lvl, needed_metal, needed_cristal, needed_deuterium, buildTime);

            return new ResourceBuilding(building_id, name, lvl, needed_metal, needed_cristal, needed_deuterium, buildTime,
                    production_per_hour);
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

        for (Building building : buildingList) {
            if (building instanceof ResourceBuilding) {
                insertNewResourceBuilding((ResourceBuilding) building);
            } else {
                insertNewBuilding(building);
            }
            logger.info(building.getName() + " has been created");
        }
        logger.info("Buildings inserted");

        final String sql = "INSERT INTO building_instance (" +
                "buildings_id, b_metal_id, b_cristal_id, b_deuterium_id, b_shipyard_id) VALUES " +
                "(?, ?, ?, ?, ?)";
        logger.info("insertNewbuilding_instance - " + sql);
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
