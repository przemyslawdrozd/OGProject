package com.example.ogame.datasource;

import com.example.ogame.models.research.Research;
import com.example.ogame.models.research.Technology;
import com.example.ogame.utils.research.ResearchHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CreatorDataSource {
    private Logger logger = LoggerFactory.getLogger(CreatorDataSource.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CreatorDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertResearch(UUID researchId) {
        Research research = new Research(researchId, ResearchHelper.createResearch());
        research.getTechList().forEach(this::insertTech);
        logger.info("Research created");

        final String sql = "INSERT INTO research VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insert Research");
        jdbcTemplate.update(sql, ResearchHelper.insertResearch(research.getResearchIdList()));
    }

    private void insertTech(Technology tech) {
        final String sql = "INSERT INTO technology VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insert tech - " + tech.toString());
        jdbcTemplate.update(sql, ResearchHelper.insertTech(tech));
    }
}
