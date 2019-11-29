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

    private Logger logger = LoggerFactory.getLogger(UserService.class);
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

    public User getUserByEmailPassword(String email, String password) {

        // if user dont exists
        if (!dataAccessService.ifThisUserExists(email, password)) {
            logger.debug("This user does not exists");
            throw new ApiRequestException("Username or Password is not correct!");
        }
        return dataAccessService.selectUserByEmailPassword(email, password);
    }

    public UserInstance getUserInstanceByUserId(String userId) {
        UUID user_id = UUID.fromString(userId);

        // Verify User ID
        if (!dataAccessService.ifUserIdExists(user_id)){
            throw new ApiRequestException("Invalid user ID!");
        }

        return dataAccessService.insertUserInstanceByUserId(user_id);
    }

    public User getUserById(String userId) {
        UUID user_id = UUID.fromString(userId);

        // Verify User ID
        if (!dataAccessService.ifUserIdExists(user_id)){
            throw new ApiRequestException("Invalid user ID!");
        }

        return dataAccessService.insertUserById(user_id);
    }

    public void startProduce() {
        dataAccessService.startProduce();
    }
}














