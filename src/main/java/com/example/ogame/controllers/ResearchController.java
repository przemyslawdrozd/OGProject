package com.example.ogame.controllers;

import com.example.ogame.models.research.Technology;
import com.example.ogame.services.ResearchService;
import com.example.ogame.utils.research.ResearchRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/research-api")
public class ResearchController {
    private Logger logger = LoggerFactory.getLogger(BuildingController.class);

    private ResearchService researchService;
    private ResearchRule researchRule;

    @Autowired
    public ResearchController(ResearchService researchService,
                              ResearchRule researchRule) {
        this.researchService = researchService;
        this.researchRule = researchRule;
    }

    @GetMapping("/{id}")
    public List<Technology> getResearch(@PathVariable("id") String id) {
        logger.info("GET research - " + id);
        UUID userId = UUID.fromString(id);
        researchRule.verifyUser(userId);
        return researchService.getResearch(userId);
    }

    @GetMapping("/{id}/{techName}")
    public Technology getTechnology(@PathVariable("id") String id,
                                    @PathVariable("techName") String techName) {
        logger.info("GET tech - " + techName);
        UUID userId = UUID.fromString(id);
        researchRule.verifyResearchApi(userId, techName);
        return researchService.getTech(userId, techName);
    }

    @PutMapping("/{id}/{techName}")
    public boolean levelUpTech(@PathVariable("id") String id,
                               @PathVariable("techName") String techName) {
        logger.info("PUT " + techName + " lvl up - " + id);
        UUID userId = UUID.fromString(id);
        researchRule.verifyResearchApi(userId, techName);
        return researchService.levelUpTech(userId, techName);
    }
}
