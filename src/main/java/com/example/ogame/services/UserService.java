package com.example.ogame.services;

import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private final VerifyDataAccess verifyDataAccess;
    private final UserDataAccess userDataAccess;

    @Autowired
    public UserService(VerifyDataAccess verifyDataAccess, UserDataAccess userDataAccess) {
        this.verifyDataAccess = verifyDataAccess;
        this.userDataAccess = userDataAccess;
    }

    public List<User> getAllUsers() {
        logger.info("userService - getAllUsers");
        return userDataAccess.selectAllStudents();
    }

    public boolean createNewUser(User newUser) {
        UUID user_id = UUID.randomUUID();
        UUID resource_id = UUID.randomUUID();
        UUID buildings_id = UUID.randomUUID();

        // Assign new Resources
        userDataAccess.insertNewResourcesToNewUser(resource_id);
        logger.info("New resources created");

        // Buildings
        userDataAccess.insertNewBuildings(buildings_id);
        logger.info("New Buildings created");

        // Create new user
        userDataAccess.insertUser(user_id, newUser);
        logger.info("New user created");

        // Create new Instance
        userDataAccess.insertNewInstance(
                user_id,
                resource_id,
                buildings_id);

        logger.info("New Instance created");

        return true;
    }

    public User getUserByEmailPassword(String email, String password) {

        // if user dont exists
        if (!verifyDataAccess.ifThisUserExists(email, password)) {
            logger.debug("This user does not exists");
            throw new ApiRequestException("Username or Password is not correct!");
        }
        return userDataAccess.selectUserByEmailPassword(email, password);
    }

    public UserInstance getUserInstanceByUserId(String userId) {
        UUID user_id = UUID.fromString(userId);

        // Verify User`s ID
        if (!verifyDataAccess.ifUserIdExists(user_id)){
            throw new ApiRequestException("Invalid user ID!");
        }

        return userDataAccess.insertUserInstanceByUserId(user_id);
    }

    public User getUserById(String userId) {
        UUID user_id = UUID.fromString(userId);

        // Verify User`s ID
        if (!verifyDataAccess.ifUserIdExists(user_id)){
            throw new ApiRequestException("Invalid user ID!");
        }

        return userDataAccess.insertUserById(user_id);
    }
}














