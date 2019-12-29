package com.example.ogame.datasource;

import com.example.ogame.models.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ResourceDataAccess extends VerifyDataAccess {

    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(ResourceDataAccess.class);

    @Autowired
    public ResourceDataAccess(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    public Resources selectResourcesByUserId(UUID user_id) {
        final String sql = "SELECT * FROM users " +
                "JOIN user_instance USING (user_id) " +
                "JOIN resources USING (resource_id) " +
                "WHERE users.user_id = ?";
//        logger.info("GET RESOURCES SQL = " + sql);
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
                resources.getResource_id());
    }
}
