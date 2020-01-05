package com.example.ogame.services;

import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResourceService {

    Logger logger = LoggerFactory.getLogger(ResourceService.class);
    private final ResourceDataAccess resourceDataAccess;

    public ResourceService(ResourceDataAccess resourceDataAccess) {
        this.resourceDataAccess = resourceDataAccess;
    }

    public int addExtraResources(UUID userId) {
        if (!resourceDataAccess.ifUserIdExists(userId))
            throw new ApiRequestException("Invalid user ID!");

        Resources resources = resourceDataAccess.selectResourcesByUserId(userId);
        resources.setMetal(resources.getMetal() + 1000);
        resources.setCristal(resources.getCristal() + 1000);
        resources.setDeuterium(resources.getDeuterium() + 1000);

        return resourceDataAccess.updateResources(resources);
    }

    public Resources getResources(UUID userId) {
        if (!resourceDataAccess.ifUserIdExists(userId))
            throw new ApiRequestException("Invalid user ID!");

        return resourceDataAccess.selectResourcesByUserId(userId);
    }

    public void updateResources(Resources resources) {
        resourceDataAccess.updateResources(resources);
    }
}
