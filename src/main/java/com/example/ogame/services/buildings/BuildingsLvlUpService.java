package com.example.ogame.services.buildings;

import com.example.ogame.datasource.VerifyDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuildingsLvlUpService {

    private Logger logger = LoggerFactory.getLogger(BuildingsLvlUpService.class);
    private final VerifyDataAccess verifyDataAccess;

    public BuildingsLvlUpService(VerifyDataAccess verifyDataAccess) {
        this.verifyDataAccess = verifyDataAccess;
    }

    // lvl up metal if enough resources
    public boolean lvlUpMetal(String userID) {
        UUID user_id = UUID.fromString(userID);

        // Get Metal building from this user
        // Check resources
        // decide to lvl up

        return false;
    }
}
