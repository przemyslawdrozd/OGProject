package com.example.ogame.datasource;

import com.example.ogame.model.Resources;
import com.example.ogame.model.User;
import com.example.ogame.model.UserInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DataAccessService {

    private final JdbcTemplate jdbcTemplate;

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


    public User insertUserByUsernamePassword(String username, String password) {
        final String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{username, password},
                (resultSet, i) -> {
                    UUID user_id = UUID.fromString(resultSet.getString("user_id"));
                    String email = resultSet.getString("email");
                    return new User(user_id, username, password, email);
                });
    }

    public boolean ifThisUserExists(String username, String password) {
        final String sql = "SELECT EXISTS ( SELECT 1 FROM users WHERE username = ? AND password = ? )";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {username, password},
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

    public Resources insertResourcesByUserId(UUID user_id) {
        final String sql = "SELECT * FROM users " +
                "JOIN user_instance USING (user_id) " +
                "JOIN resources USING (resource_id) " +
                "WHERE users.user_id = ?";

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

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {user_id},
                (resultSet, i) -> {
                    UUID resources_id = UUID.fromString(resultSet.getString("resource_id"));
                    return new UserInstance(user_id, resources_id);
                }
        );
    }

    public int updateResources(Resources resources) {
        final String sql = "UPDATE resources " +
                "SET metal = ?, cristal = ?, deuterium = ? " +
                "WHERE resource_id = ?";

        return jdbcTemplate.update(
                sql,
                resources.getMetal(),
                resources.getCristal(),
                resources.getDeuterium(),
                resources.getResource_id());
    }
}















