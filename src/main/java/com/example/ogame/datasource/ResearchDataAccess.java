package com.example.ogame.datasource;

import com.example.ogame.models.research.Technology;
import com.example.ogame.utils.research.ResearchHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ResearchDataAccess {
    private Logger logger = LoggerFactory.getLogger(ResourceDataAccess.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResearchDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Technology> selectResearch(UUID userId) {
        return getTechIds(getResearchId(userId))
                .stream()
                .map(this::selectTech)
                .collect(Collectors.toList());
    }

    public Technology selectTechByName(UUID userId, String techName) {
        String tech_name = techName.replace(" ", "_").toLowerCase();
        UUID researchId = getResearchId(userId);
        final String sql = "SELECT " + tech_name + " FROM research WHERE research_id = ?";

        return jdbcTemplate.queryForObject(sql,
                new Object[] {researchId},
                (resultSet, i) -> selectTechById(UUID.fromString(resultSet.getString(tech_name))));
    }

    private Technology selectTechById(UUID techId) {
        final String sql = "SELECT * FROM technology WHERE tech_id = ?";
        return jdbcTemplate.queryForObject(sql,
                new Object[] {techId},
                getTechRowMapper()
        );
    }

    private Technology selectTech(UUID techId) {
        final String sql = "SELECT * FROM technology WHERE tech_id = ?";
        return jdbcTemplate.queryForObject(sql,
                new Object[] {techId},
                getTechRowMapper()
        );
    }

    private RowMapper<Technology> getTechRowMapper() {
        return (rs, s) -> new Technology(
                UUID.fromString(rs.getString("tech_id")),
                rs.getString("tech_name"),
                rs.getInt("lvl"),
                rs.getInt("metal"),
                rs.getInt("cristal"),
                rs.getInt("deuterium"),
                rs.getString("build_time"),
                rs.getString("next_build_time"),
                rs.getInt("is_able"));
    }

    public List<UUID> getTechIds(UUID researchId) {
        final String sql = "SELECT * FROM research WHERE research_id = ?";

        return jdbcTemplate.queryForList(sql, researchId)
                .get(0).values()
                .stream().skip(1)
                .map(id -> (UUID) id)
                .collect(Collectors.toList());
    }

    public UUID getResearchId(UUID userId) {
        final String sql = "SELECT research_id FROM user_instance WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {userId},
                (rs, i) -> UUID.fromString(rs.getString("research_id")));
    }

    public void updateTech(Technology tech) {
        String newTime = getResearchNextTime(tech.getTechId());
        String nextBuildTime = ResearchHelper.nextBuildTime(newTime);
        final String sql = "UPDATE technology SET " +
                "lvl = ?, metal = ?, cristal = ?, deuterium = ?, build_time = ?, next_build_time = ?, is_able = ? " +
                "WHERE tech_id = ?";
        logger.info("UPDATE tech = " + sql);
        jdbcTemplate.update(sql, ResearchHelper.updateTech(tech, newTime, nextBuildTime));
    }

    public void updateCountDawn(Technology tech) {
        final String sql = "UPDATE technology SET " +
                "build_time = ? WHERE tech_id = ?";
        jdbcTemplate.update(sql, new Object[] { tech.getBuildTime(), tech.getTechId()});
    }

    public String getResearchNextTime(UUID techId) {
        final String sql = "SELECT next_build_time FROM technology WHERE tech_id = ?";
        return jdbcTemplate.queryForObject(sql, new UUID[]{techId}, String.class);
    }

    public void blockResearching(UUID techId) {
        final String sql = "UPDATE technology SET " +
                "is_able = ? WHERE tech_id = ?";
        jdbcTemplate.update(sql, new Object[] { 0, techId});
    }

    public void unblockResearching(UUID techId) {
        final String sql = "UPDATE technology SET " +
                "is_able = ? WHERE tech_id = ?";
        jdbcTemplate.update(sql, new Object[] { 1, techId});
    }

    public void isResearching(UUID techId){
        final String sql = "UPDATE technology SET " +
                "is_able = ? WHERE tech_id = ?";
        jdbcTemplate.update(sql, new Object[] { 2, techId});
    }
}
