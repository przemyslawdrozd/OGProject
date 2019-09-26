package com.example.ogame.service;

import com.example.ogame.datasource.DataAccessService;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.model.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResourceService {

    Logger logger = LoggerFactory.getLogger(ResourceService.class);
    private final DataAccessService dataAccessService;

    public ResourceService(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    public int addExtraResources(String userId) {
        UUID user_id = UUID.fromString(userId);
        if (!dataAccessService.ifUserIdExists(user_id))
            throw new ApiRequestException("Invalid user ID!");

        Resources resources = dataAccessService.selectResourcesByUserId(user_id);
        resources.setMetal(resources.getMetal() + 1000);
        resources.setCristal(resources.getCristal() + 1000);
        resources.setDeuterium(resources.getDeuterium() + 1000);

        return dataAccessService.updateResources(resources);
    }

    public Resources getResources(String userId) {
        UUID user_id = UUID.fromString(userId);
        if (!dataAccessService.ifUserIdExists(user_id))
            throw new ApiRequestException("Invalid user ID!");

        return dataAccessService.selectResourcesByUserId(user_id);
    }
}
