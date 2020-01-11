package com.example.ogame.datasource;

import com.example.ogame.models.Resources;
import com.example.ogame.utils.resources.ResourcesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.example.ogame.utils.resources.ResourcesHelper.*;

@Repository
public class ResourceDataAccess {
    private Logger logger = LoggerFactory.getLogger(ResourceDataAccess.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResourceDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TODO Change these queries
    public Resources selectResources(UUID userId) {
        final String sql = "SELECT * FROM users " +
                "JOIN user_instance USING (user_id) " +
                "JOIN resources USING (resource_id) " +
                "WHERE users.user_id = ?";
//        logger.info("GET RESOURCES SQL = " + sql);
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {userId},
                (resultSet, i) -> {
                    UUID resource_id = UUID.fromString(resultSet.getString("resource_id"));
                    int metal = resultSet.getInt("metal");
                    int cristal = resultSet.getInt("cristal");
                    int deuterium = resultSet.getInt("deuterium");

                    return new Resources(resource_id, metal, cristal, deuterium);
                }
        );
    }

    public int updateResources(Resources resources) {
        final String sql = "UPDATE resources " +
                "SET metal = ?, cristal = ?, deuterium = ? " +
                "WHERE resource_id = ?";
//        logger.info("UPDATE RESOURCES SQL = " + sql);
        return jdbcTemplate.update(
                sql,
                resources.getMetal(),
                resources.getCristal(),
                resources.getDeuterium(),
                resources.getResourceId());
    }
}
