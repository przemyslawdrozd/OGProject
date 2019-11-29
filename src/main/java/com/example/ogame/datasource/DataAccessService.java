package com.example.ogame.datasource;

import com.example.ogame.model.*;
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
public class DataAccessService {

    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(DataAccessService.class);


    @Autowired
    public DataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> selectAllStudents() {
        final String sql = "SELECT user_id, username, password, email FROM users";

        return jdbcTemplate.query(
                sql,
                getUserFromDb());
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

    public void insertUser(UUID user_id, User newUser) {

        final String sql = "INSERT INTO users (user_id, username, password, email) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                user_id,
                newUser.getUsername(),
                newUser.getPassword(),
                newUser.getEmail());
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

    public Resources selectResourcesByUserId(UUID user_id) {
        final String sql = "SELECT * FROM users " +
                "JOIN user_instance USING (user_id) " +
                "JOIN resources USING (resource_id) " +
                "WHERE users.user_id = ?";
        logger.info("GET RESOURCES SQL = " + sql);
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {user_id},
                (resultSet, i) -> {
                    UUID resource_id = UUID.fromString(resultSet.getString("resource_id"));
                    int metal = resultSet.getInt("metal");
                    int cristal = resultSet.getInt("cristal");
                    int deuterium = resultSet.getInt("deuterium");

                    return new Resources(resource_id, metal, cristal, deuterium);
                }
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

    public int updateResources(Resources resources) {
        final String sql = "UPDATE resources " +
                "SET metal = ?, cristal = ?, deuterium = ? " +
                "WHERE resource_id = ?";
        logger.info("UPDATE RESOURCES SQL = " + sql);
        return jdbcTemplate.update(
                sql,
                resources.getMetal(),
                resources.getCristal(),
                resources.getDeuterium(),
                resources.getResource_id());
    }

    public int insertNewResourcesToNewUser(UUID resource_id) {
        final String sql = "INSERT INTO resources (" +
                "resource_id, metal, cristal, deuterium) " +
                "VALUES (?, ?, ?, ?)";
        logger.info("SQL = " + sql);
        Resources resources = new Resources(resource_id, 500, 500, 0);

        return jdbcTemplate.update(
                sql,
                resource_id,
                resources.getMetal(),
                resources.getCristal(),
                resources.getDeuterium());

    }

    public void insertNewInstance(UUID user_id, UUID resource_id, UUID buildings_id) {
        final String sql = "INSERT INTO user_instance (user_id, resource_id, buildings_id) " +
                "VALUES (?, ?, ?)";
        logger.info("SQL = " + sql);
        jdbcTemplate.update(sql,
                user_id,
                resource_id,
                buildings_id);
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
        logger.info("insertNewBuilding -> " + sql);
        jdbcTemplate.update(
                sql,
                building.getBuilding_id(),
                building.getName(),
                building.getLevel(),
                building.getNeededMetal(),
                building.getNeededCristal(),
                building.getNeededDeuterium());
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

    public void startProduce() {

        MyThread myThread = new MyThread(this);
        myThread.start();
    }

    public static class MyThread extends Thread {

        private final DataAccessService dataAccessService;

        public MyThread(DataAccessService dataAccessService) {
            this.dataAccessService = dataAccessService;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {

                for (User user : dataAccessService.selectAllStudents()){
                    System.out.println(user.toString());
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}















