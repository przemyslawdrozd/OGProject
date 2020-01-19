package com.example.ogame.utils.galaxy;

import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.utils.VerifyRule;
import org.springframework.stereotype.Component;

@Component
public class PlanetRule extends VerifyRule {

    public PlanetRule(VerifyDataAccess verifyDataAccess, UserDataAccess userDataAccess) {
        super(verifyDataAccess, userDataAccess);
    }
}
