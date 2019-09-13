package com.example.ogame.service;

import com.example.ogame.datasource.DataAccessService;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final DataAccessService dataAccessService;

    @Autowired
    public UserService(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    public List<User> getAllUsers() {
        return dataAccessService.selectAllStudents();
    }

    public void createNewUser(User newUser) {
        UUID user_id = UUID.randomUUID();
        UUID resource_id = UUID.randomUUID();
        UUID buildings_id = UUID.randomUUID();

        // Assign new Resources
        dataAccessService.insertNewResourcesToNewUser(resource_id);
        logger.info("New resources created");

        // Buildings
        dataAccessService.insertNewBuildings(buildings_id);
        logger.info("New Buildings created");

        // Create new user
        dataAccessService.insertUser(user_id, newUser);
        logger.info("New user created");

        // Create new Instance
        dataAccessService.insertNewInstance(
                user_id,
                resource_id,
                buildings_id);

        logger.info("New Instance created");
    }

    public User getUserByUsernamePassword(String username, String password) {

        // if user dont exists
        if (!dataAccessService.ifThisUserExists(username, password)) {
            logger.debug("This user does not exists");
            throw new ApiRequestException("Username or Password is not correct!");
        }
        return dataAccessService.selectUserByUsernamePassword(username, password);
    }

    public Resources getResourcesByUserId(String userID) {
        UUID user_id = UUID.fromString(userID);

        // Verify User ID
        if (!dataAccessService.ifUserIdExists(user_id)){
            throw new ApiRequestException("Invalid user ID!");
        }

        return dataAccessService.selectResourcesByUserId(user_id);
    }

    public UserInstance getUserInstanceByUserId(String userId) {
        UUID user_id = UUID.fromString(userId);

        // Verify User ID
        if (!dataAccessService.ifUserIdExists(user_id)){
            throw new ApiRequestException("Invalid user ID!");
        }

        return dataAccessService.insertUserInstanceByUserId(user_id);
    }

    public int addExtraResources(String userId) {

        UUID user_id = UUID.fromString(userId);
        Resources resources = dataAccessService.selectResourcesByUserId(user_id);

        resources.setMetal(resources.getMetal() + 1000);
        resources.setCristal(resources.getCristal() + 1000);
        resources.setDeuterium(resources.getDeuterium() + 1000);

        return dataAccessService.updateResources(resources);
    }

    public List<Building> getBuildings(String userID) {
        logger.info("getBuildings from " + userID);
        UUID user_id = UUID.fromString(userID);
        return dataAccessService.selectBuildings(user_id);
    }

    public Building getMetalBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return dataAccessService.insertMetalBuilding(user_id);
    }

    public Building getCristalBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return dataAccessService.insertCristalBuilding(user_id);
    }

    public Building getDeuteriumBuilding(String userID) {
        UUID user_id = UUID.fromString(userID);
        return dataAccessService.insertDeuteriumBuilding(user_id);
    }
}














