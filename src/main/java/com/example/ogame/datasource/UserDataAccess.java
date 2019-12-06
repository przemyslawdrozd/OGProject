package com.example.ogame.datasource;

import com.example.ogame.model.Building;
import com.example.ogame.model.Resources;
import com.example.ogame.model.User;
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
public class UserDataAccess {

    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(UserDataAccess.class);

    @Autowired
    public UserDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> selectAllStudents() {
        final String sql = "SELECT user_id, username, password, email FROM users";
        logger.trace("selectAllStudents - " + sql);

        return jdbcTemplate.query(
                sql,
                getUserFromDb());
    }

    public int insertNewResourcesToNewUser(UUID resource_id) {
        final String sql = "INSERT INTO resources (" +
                "resource_id, metal, cristal, deuterium) " +
                "VALUES (?, ?, ?, ?)";
        logger.info("insertNewResourcesToNewUser - " + sql);
        Resources resources = new Resources(resource_id, 500, 500, 0);

        return jdbcTemplate.update(
                sql,
                resource_id,
                resources.getMetal(),
                resources.getCristal(),
                resources.getDeuterium());
    }

    public void insertNewBuildings(UUID building_id) {
        // Create started buildings
        List<Building> buildingList = new ArrayList<>();
        buildingList.add(new Building(UUID.randomUUID(), "Metal Mine", 1, 100, 30,0));
        buildingList.add(new Building(UUID.randomUUID(), "Cristal Mine", 1, 120, 60,0));
        buildingList.add(new Building(UUID.randomUUID(), "Deuterium Synthesizer", 1, 150, 40,0));

        for (Building building: buildingList) {
            insertNewBuilding(building);
        }
        logger.info("Buildings inserted");

        final String sql = "INSERT INTO buildings (" +
                "buildings_id, b_metal_id, b_cristal_id, b_deuterium_id) VALUES " +
                "(?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                building_id,
                buildingList.get(0).getBuilding_id(),
                buildingList.get(1).getBuilding_id(),
                buildingList.get(2).getBuilding_id());
    }

    private void insertNewBuilding(Building building) {
        final String sql = "INSERT INTO building (" +
                "building_id, namee, lvl, needed_metal, needed_cristal, needed_deuterium) VALUES " +
                "(?, ?, ?, ?, ?, ?)";
        logger.info("insertNewBuilding - " + sql);
        jdbcTemplate.update(
                sql,
                building.getBuilding_id(),
                building.getName(),
                building.getLevel(),
                building.getNeededMetal(),
                building.getNeededCristal(),
                building.getNeededDeuterium());
    }

    public void insertUser(UUID user_id, User newUser) {
        final String sql = "INSERT INTO users (user_id, username, password, email) VALUES (?, ?, ?, ?)";
        logger.info("insertUser - " + sql);
        jdbcTemplate.update(
                sql,
                user_id,
                newUser.getUsername(),
                newUser.getPassword(),
                newUser.getEmail());
    }

    public void insertNewInstance(UUID user_id, UUID resource_id, UUID buildings_id) {
        final String sql = "INSERT INTO user_instance (user_id, resource_id, buildings_id) " +
                "VALUES (?, ?, ?)";
        logger.info("insertNewInstance = " + sql);
        jdbcTemplate.update(sql,
                user_id,
                resource_id,
                buildings_id);
    }

    private RowMapper<User> getUserFromDb() {
        return (resultSet, i) -> {
            UUID user_id = UUID.fromString(resultSet.getString("user_id"));
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            return new User(user_id, username, password, email);
        };
    }
}
