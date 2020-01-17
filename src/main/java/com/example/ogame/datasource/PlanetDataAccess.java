package com.example.ogame.datasource;

import com.example.ogame.models.Planet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

        return jdbcTemplate.query(sql,
                (rs, i) -> new Planet(
                        UUID.fromString(rs.getString("planet_id")),
                        rs.getInt("galaxy_position"),
                        rs.getInt("planetary_system"),
                        rs.getInt("planet_position")));
    }

}
