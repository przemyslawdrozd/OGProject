package com.example.ogame.datasource;

import com.example.ogame.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class VerifyDataAccess {

    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(VerifyDataAccess.class);

    @Autowired
    public VerifyDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}