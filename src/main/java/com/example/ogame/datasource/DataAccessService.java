package com.example.ogame.datasource;

import com.example.ogame.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class DataAccessService {

    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(DataAccessService.class);


    @Autowired
    public DataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User selectUserByEmailPassword(String email, String password) {
        final String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{email, password},
                (resultSet, i) -> {
                    UUID user_id = UUID.fromString(resultSet.getString("user_id"));
                    String username = resultSet.getString("username");
                    return new User(user_id, username, password, email);
                });
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
                    return new User(user_id, username, email);
                }
        );
    }

    public boolean ifThisUserExists(String email, String password) {
        final String sql = "SELECT EXISTS ( SELECT 1 FROM users WHERE email = ? AND password = ? )";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {email, password},
                (resultSet, i) -> resultSet.getBoolean(1)
        );
    }

    public boolean ifUserIdExists(UUID userID) {
        final String sql = "SELECT EXISTS ( SELECT 1 FROM users WHERE user_id = ? )";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{userID},
                (resultSet, i) -> resultSet.getBoolean(1)
        );
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

    public List<Building> selectBuildings(UUID user_id) {
        // TODO In future Try to make shorter query
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
}