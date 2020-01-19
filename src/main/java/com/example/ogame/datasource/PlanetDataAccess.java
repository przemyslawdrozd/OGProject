package com.example.ogame.datasource;

import com.example.ogame.models.Planet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class PlanetDataAccess {
    private Logger logger = LoggerFactory.getLogger(PlanetDataAccess.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PlanetDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Planet> selectGalaxy() {
        final String sql = "SELECT * FROM planet";
        return jdbcTemplate.query(sql, getPlanetRowMapper());
    }

    public Planet selectPlanet(UUID userId) {
        UUID planetId = selectPlanetId(userId);
        final String sql = "SELECT * FROM planet WHERE planet_id = ?";

        return jdbcTemplate.queryForObject(sql,
                new Object[] {planetId},
                getPlanetRowMapper());
    }

    private UUID selectPlanetId(UUID userId) {
        final String sql = "SELECT planet_id FROM user_instance WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[] {userId},
                (rs, i) -> UUID.fromString(rs.getString("planet_id")));
    }

    private RowMapper<Planet> getPlanetRowMapper() {
        return (rs, i) -> new Planet(
                UUID.fromString(rs.getString("planet_id")),
                rs.getInt("galaxy_position"),
                rs.getInt("planetary_system"),
                rs.getInt("planet_position"));
    }
}
