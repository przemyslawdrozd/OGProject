package com.example.ogame.utils.fleet;

import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.utils.VerifyRule;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class FleetRule extends VerifyRule {
    private final Logger logger = LoggerFactory.getLogger(FleetRule.class);

    public FleetRule(VerifyDataAccess verifyDataAccess,
                     UserDataAccess userDataAccess) {
        super(verifyDataAccess, userDataAccess);
    }

    public void verifyFleetApi(UUID userId, String shipName) {
        verifyUser(userId);

        if (!EnumUtils.isValidEnum(ShipName.class, shipName)) {
            logger.warn("Wrong Ship name!");
            throw new ApiRequestException(shipName + " does not exists");
        }
    }
}
