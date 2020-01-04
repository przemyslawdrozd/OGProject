package com.example.ogame.datasource;

import com.example.ogame.models.Resources;
import com.example.ogame.models.User;
import com.example.ogame.models.UserInstance;
import com.example.ogame.services.ApplicationUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDataAccess implements ApplicationUserDao {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserDataAccess.class);

    @Autowired
    public UserDataAccess(JdbcTemplate jdbcTemplate,
                          PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> selectAllUsers() {
        final String sql = "SELECT user_id, username, password, email FROM users";
        logger.trace("selectAllStudents - " + sql);

        return jdbcTemplate.query(
                sql,
                getUserFromDb());
    }

    public List<UUID> selectAllUsersUUID() {
        final String sql = "SELECT user_id FROM users";

        return jdbcTemplate.query(
                sql,
                (rs, i) -> UUID.fromString(rs.getString("user_id")));
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
                    UUID fleet_id = UUID.fromString((resultSet.getString("fleet_id"))); // Do migration
                    return new UserInstance(user_id, resources_id, buildings_id, fleet_id);
                }
        );
    }

    public User selectUserByEmailPassword(String username, String password) {
        final String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{username, password},
                (resultSet, i) -> {
                    UUID user_id = UUID.fromString(resultSet.getString("user_id"));
                    String email = resultSet.getString("email");
                    User user = new User(username, password, email);
                    user.setUser_id(user_id);
                    return user;
                });
    }

    // TODO 15 modify method by adding fleet id
    public void insertNewInstance(UUID user_id, UUID resource_id, UUID buildings_id, UUID fleet_id) {
        final String sql = "INSERT INTO user_instance (user_id, resource_id, buildings_id, fleet_id) " +
                "VALUES (?, ?, ?, ?)";
        logger.info("insertNewInstance = " + sql);
        jdbcTemplate.update(sql,
                user_id,
                resource_id,
                buildings_id,
                fleet_id);
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

    @Override
    public Optional<User> selectUserByUsername(String username) {
        final String sql = "SELECT * FROM users WHERE username = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{username},
                getUserFromDb()
        )
                .stream()
                .filter(user -> username.equals(user.getUsername())).findFirst();
    }

    private RowMapper<User> getUserFromDb() {
        return (resultSet, i) -> {
            UUID user_id = UUID.fromString(resultSet.getString("user_id"));
            String username = resultSet.getString("username");
            String password = passwordEncoder.encode(resultSet.getString("password"));
            String email = resultSet.getString("email");

            User user = new User(username, password, email);
            user.setUser_id(user_id);
            return user;
        };
    }
}
