package com.example.ogame.utils.facilities;

import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.utils.VerifyRule;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class FacilitiesRule extends VerifyRule {
    private final Logger logger = LoggerFactory.getLogger(FacilitiesRule.class);

    @Autowired
    public FacilitiesRule(VerifyDataAccess verifyDataAccess,
                          UserDataAccess userDataAccess) {
        super(verifyDataAccess, userDataAccess);
    }

    public void verifyFacilitiesApi(UUID userId, String buildingName) {
        verifyUser(userId);

        if (!EnumUtils.isValidEnum(BuildingName.class, buildingName)) {
            logger.warn("Wrong Building name!");
            throw new ApiRequestException(buildingName + " does not exists");
        }
    }
}
