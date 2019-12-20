package com.example.ogame.datasource;

import com.example.ogame.models.Building;
import com.example.ogame.models.Resources;
import com.example.ogame.models.User;
import com.example.ogame.models.UserInstance;
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

    public UserInstance insertUserInstanceByUserId(UUID user_id) {
        final String sql = "SELECT * FROM users " +
                "JOIN user_instance USING (user_id) " +
                "WHERE users.user_id = ?";
        logger.info("SQL = " + sql);
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {user_id},
                (resultSet, i) -> {
                    UUID resources_id = UUID.fromString(resultSet.getString("resource_id"));
                    UUID buildings_id = UUID.fromString(resultSet.getString("buildings_id"));
                    return new UserInstance(user_id, resources_id, buildings_id);
                }
        );
    }

    public User selectUserByEmailPassword(String email, String password) {
        final String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{email, password},
                (resultSet, i) -> {
                    UUID user_id = UUID.fromString(resultSet.getString("user_id"));
                    String username = resultSet.getString("username");
                    User user = new User(username, password, email);
                    user.setUser_id(user_id);
                    return user;
                });
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

    public User insertUserById(UUID user_id) {
        final String sql = "SELECT * FROM users " +
                "WHERE user_id = ?";
        logger.info("GET user: " + sql);
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {user_id},
                (resultSet, i) -> {
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User user = new User(username, password, email);
                    user.setUser_id(user_id);
                    return user;
                }
        );
    }

    private RowMapper<User> getUserFromDb() {
        return (resultSet, i) -> {
            UUID user_id = UUID.fromString(resultSet.getString("user_id"));
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");

            User user = new User(username, password, email);
            user.setUser_id(user_id);
            return user;
        };
    }
}
