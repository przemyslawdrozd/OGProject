package com.example.ogame.tools;

import com.example.ogame.datasource.ResearchDataAccess;
import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.models.research.Technology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class ResearchScheduler {
    private Logger logger = LoggerFactory.getLogger(ResearchScheduler.class);

    private ResearchDataAccess researchDataAccess;
    private UserDataAccess userDataAccess;

    public ResearchScheduler(ResearchDataAccess researchDataAccess, UserDataAccess userDataAccess) {
        this.researchDataAccess = researchDataAccess;
        this.userDataAccess = userDataAccess;
    }

    @Scheduled(fixedDelay = 1000)
    public void researching() {
        List<UUID> users = userDataAccess.selectAllUsersUUID();

        users.stream().skip(1).forEach(userId -> {
                    List<Technology> technologies = researchDataAccess.selectResearch(userId);
                    technologies.forEach(tech -> {
                        if (tech.getIsAbleToBuild() == 2) {
                            if (tech.countDown()) {
                                researchDataAccess.updateCountDown(tech);
                            } else {
                                tech.lvlUpTech();
                                researchDataAccess.updateTech(tech);
                                unblockTech(userId);
                            }
                        }
                    });
                });
    }

    private void unblockTech(UUID userId) {
        List<Technology> technologies = researchDataAccess.selectResearch(userId);
        technologies.forEach(tech -> researchDataAccess.unblockResearching(tech.getTechId()));
    }

}