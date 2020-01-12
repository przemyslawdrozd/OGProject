package com.example.ogame.utils.research;

import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.utils.VerifyRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResearchRule extends VerifyRule {
    private Logger logger = LoggerFactory.getLogger(ResearchRule.class);

    @Autowired
    public ResearchRule(VerifyDataAccess verifyDataAccess,
                        UserDataAccess userDataAccess) {
        super(verifyDataAccess, userDataAccess);
    }

    public void verifyResearchApi(UUID userId, String techName) {
        verifyUser(userId);

        if (!TechName.checkTechName(techName.toLowerCase())) {
            logger.warn("Wrong Technology name!");
            throw new ApiRequestException(techName + " does not exists");
        }
    }
}
