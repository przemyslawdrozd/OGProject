package com.example.ogame.datasource;

import com.example.ogame.models.facilities.Building;
import com.example.ogame.utils.facilities.BuildingName;
import com.example.ogame.utils.facilities.FacilitiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FacilitiesDataAccess {

    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(FacilitiesDataAccess.class);

    @Autowired
    public FacilitiesDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Building> selectFacilities(UUID userId) {
        return getBuildingIds(getFacilitiesId(userId))
                .stream()
                .map(this::selectBuildingById)
                .collect(Collectors.toList());
    }

    private List<UUID> getBuildingIds(UUID facilitiesId) {
        final String sql = "SELECT * FROM facilities WHERE facilities_id = ?";
        return jdbcTemplate.queryForList(sql, facilitiesId)
                .get(0).values()
                .stream().skip(1)
                .map(id -> (UUID) id)
                .collect(Collectors.toList());
    }

    private UUID getFacilitiesId(UUID userId) {
        final String sql = "SELECT facilities_id FROM user_instance WHERE user_id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {userId},
                (rs, i) -> UUID.fromString(rs.getString("facilities_id")));
    }

    public Building selectBuildingById(UUID buildingId) {
        final String sql = "SELECT * FROM building WHERE building_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {buildingId},
                getBuildingRowMapper()
        );
    }

    private RowMapper<Building> getBuildingRowMapper() {
        return (rs, i) -> new Building(
                UUID.fromString(rs.getString("building_id")),
                Enum.valueOf(BuildingName.class, rs.getString("building_name")),
                rs.getInt("lvl"),
                rs.getInt("needed_metal"),
                rs.getInt("needed_cristal"),
                rs.getInt("needed_deuterium"),
                rs.getInt("production_per_hour"),
                rs.getString("build_time"),
                rs.getString("next_build_time"),
                rs.getInt("is_able"));
    }

    public Building selectBuilding(UUID userId, String buildingName) {
        UUID facilitiesId = getFacilitiesId(userId);
        final String sql = "SELECT " + buildingName +
                " FROM facilities WHERE facilities_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {facilitiesId},
                (rs, i) -> selectBuildingById(UUID.fromString(rs.getString(buildingName)))
        );
    }

    public int updateBuilding(Building building) {
        String newTime = getResearchNextTime(building.getBuildingId());
        String nextBuildTime = FacilitiesHelper.nextBuildTime(newTime);
        final String sql = "UPDATE building " +
                "SET lvl = ?, needed_metal = ?, needed_cristal = ?, needed_deuterium = ?, production_per_hour = ?, " +
                "build_time = ?, next_build_time = ?, is_able = ?" +
                "WHERE building_id = ?";
        logger.info("UPDATE buildingLvlUp SQL = " + sql);
        return jdbcTemplate.update(sql, FacilitiesHelper.updateBuilding(building, newTime, nextBuildTime));
    }

    public String getResearchNextTime(UUID buildingId) {
        final String sql = "SELECT next_build_time FROM building WHERE building_id = ?";
        return jdbcTemplate.queryForObject(sql, new UUID[]{buildingId}, String.class);
    }

    public void updateCountDown(Building b) {
        final String sql = "UPDATE building SET " +
                "build_time = ? WHERE building_id = ?";
        jdbcTemplate.update(sql, new Object[] { b.getBuildTime(), b.getBuildingId()});
    }

    public void blockBuilding(UUID buildingId) {
        final String sql = "UPDATE building SET " +
                "is_able = ? WHERE building_id = ?";
        jdbcTemplate.update(sql, new Object[] { 0, buildingId});
    }

    public void unblockBuilding(UUID buildingId) {
        final String sql = "UPDATE building SET " +
                "is_able = ? WHERE building_id = ?";
        jdbcTemplate.update(sql, new Object[] { 1, buildingId});
    }

    public void isBuilding(UUID buildingId){
        final String sql = "UPDATE building SET " +
                "is_able = ? WHERE building_id = ?";
        jdbcTemplate.update(sql, new Object[] { 2, buildingId});
    }
}
