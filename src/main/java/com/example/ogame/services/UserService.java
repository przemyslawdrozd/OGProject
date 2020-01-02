package com.example.ogame.services;

import com.example.ogame.datasource.BuildingDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private final VerifyDataAccess verifyDataAccess;
    private final UserDataAccess userDataAccess;
    private final BuildingDataAccess buildingDataAccess;
    private final ApplicationUserDao applicationUserDao;

    @Autowired
    public UserService(VerifyDataAccess verifyDataAccess,
                       UserDataAccess userDataAccess,
                       BuildingDataAccess buildingDataAccess,
                       ApplicationUserDao applicationUserDao) {
        this.verifyDataAccess = verifyDataAccess;
        this.userDataAccess = userDataAccess;
        this.buildingDataAccess = buildingDataAccess;
        this.applicationUserDao = applicationUserDao;
    }

    public List<User> getAllUsers() {
        logger.info("userService - getAllUsers");
        return userDataAccess.selectAllUsers();
    }

    // TODO find a issue with the same email:
    // When user`s account is created with used email exception shows but instance is created
    public boolean createNewUser(User newUser) {
        UUID user_id = UUID.randomUUID();
        UUID resource_id = UUID.randomUUID();
        UUID buildings_id = UUID.randomUUID();

        // Assign new Resources
        userDataAccess.insertNewResourcesToNewUser(resource_id);
        logger.info("New resources created");

        // Buildings
        buildingDataAccess.insertNewBuildings(buildings_id);
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

    public User getUserByUsernamePassword(String username, String password) {

        // if user dont exists
        if (!verifyDataAccess.ifThisUserExists(username, password)) {
            logger.debug("This user does not exists");
            throw new ApiRequestException("Username or Password is not correct!");
        }
        return userDataAccess.selectUserByEmailPassword(username, password);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao
                .selectUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}














