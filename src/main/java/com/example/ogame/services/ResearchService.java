package com.example.ogame.services;

import com.example.ogame.datasource.ResearchDataAccess;
import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.Resources;
import com.example.ogame.models.research.Technology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResearchService {
    private Logger logger = LoggerFactory.getLogger(ResourceService.class);

    private ResearchDataAccess researchDataAccess;
    private ResourceDataAccess resourceDataAccess;

    @Autowired
    public ResearchService(ResearchDataAccess researchDataAccess,
                           ResourceDataAccess resourceDataAccess) {
        this.researchDataAccess = researchDataAccess;
        this.resourceDataAccess = resourceDataAccess;
    }

    public List<Technology> getResearch(UUID userId) {
        logger.info("getResearch");
        return researchDataAccess.selectResearch(userId);
    }

    public Technology getTech(UUID userId, String techName) {
        return researchDataAccess.selectTechByName(userId, techName);
    }

    public boolean levelUpTech(UUID userId, String techName) {

        Technology tech = researchDataAccess.selectTechByName(userId, techName);
        Resources res = resourceDataAccess.selectResources(userId);

        if (tech.getNeededMetal() < res.getMetal() &&
            tech.getNeededCristal() < res.getCristal() &&
            tech.getNeededDeuterium() < res.getDeuterium() &&
            tech.isAbleToBuild() == 1) {

            blockRestOfTech(userId);

            res.utilizeForBuild(
                    tech.getNeededMetal(),
                    tech.getNeededCristal(),
                    tech.getNeededDeuterium()
            );

            resourceDataAccess.updateResources(res);
            researchDataAccess.isResearching(tech.getTechId());
            logger.info("Tech able to researching: 2");
            return true;
        }
        throw new ApiRequestException("Not enough Resources or is researching");
    }

    private void blockRestOfTech(UUID userId) {
        List<Technology> technologies = researchDataAccess.selectResearch(userId);
        technologies.forEach(tech -> researchDataAccess.blockResearching(tech.getTechId()));
        logger.info("Tech are blocked: 0");
    }
}
