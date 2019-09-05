package com.example.ogame.datasource;

import com.example.ogame.model.User;
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
}















