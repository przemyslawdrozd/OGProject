package com.example.ogame.services;

import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.models.Resources;
import com.example.ogame.utils.VerifyRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResourceService {
    Logger logger = LoggerFactory.getLogger(ResourceService.class);

    private final ResourceDataAccess resourceDataAccess;
    private final VerifyRule verifyRule;

    public ResourceService(ResourceDataAccess resourceDataAccess,
                           VerifyRule verifyRule) {
        this.resourceDataAccess = resourceDataAccess;
        this.verifyRule = verifyRule;
    }

    public int addExtraResources(UUID userId) {
        verifyRule.verifyUser(userId);

        var res = resourceDataAccess.selectResources(userId);
        res.setMetal(res.getMetal() + 10000);
        res.setCristal(res.getCristal() + 10000);
        res.setDeuterium(res.getDeuterium() + 10000);
        logger.info("Resources updated");
        return resourceDataAccess.updateResources(res);
    }

    public Resources getResources(UUID userId) {
        verifyRule.verifyUser(userId);
        return resourceDataAccess.selectResources(userId);
    }

    public void updateResources(Resources resources) {
        resourceDataAccess.updateResources(resources);
    }
}
