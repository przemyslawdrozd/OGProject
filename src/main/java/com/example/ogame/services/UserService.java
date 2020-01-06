package com.example.ogame.services;

import com.example.ogame.datasource.BuildingDataAccess;
import com.example.ogame.datasource.FleetDataAccess;
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
    private final FleetDataAccess fleetDataAccess;

    @Autowired
    public UserService(VerifyDataAccess verifyDataAccess,
                       UserDataAccess userDataAccess,
                       BuildingDataAccess buildingDataAccess,
                       ApplicationUserDao applicationUserDao,
                       FleetDataAccess fleetDataAccess) {
        this.verifyDataAccess = verifyDataAccess;
        this.userDataAccess = userDataAccess;
        this.buildingDataAccess = buildingDataAccess;
        this.applicationUserDao = applicationUserDao;
        this.fleetDataAccess = fleetDataAccess;
    }

    public List<User> getAllUsers() {
        logger.info("userService - getAllUsers");
        return userDataAccess.selectAllUsers();
    }

    public boolean createNewUser(User newUser) {
        UUID user_id = UUID.randomUUID();
        UUID resource_id = UUID.randomUUID();
        UUID buildings_id = UUID.randomUUID();
        UUID fleet_id = UUID.randomUUID();
        verifyNewUser(newUser);

        fleetDataAccess.insertFleet(fleet_id);

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
                buildings_id,
                fleet_id);

        logger.info("New Instance created");

        return true;
    }

    public User getUserByUsernamePassword(String username, String password) {

        if (!verifyDataAccess.ifThisUserExists(username, password)) {
            logger.debug("This user does not exists");
            throw new ApiRequestException("Username or Password is not correct!");
        }
        return userDataAccess.selectUserByEmailPassword(username, password);
    }

    public UserInstance getUserInstanceByUserId(String userId) {
        UUID user_id = UUID.fromString(userId);

        if (!verifyDataAccess.ifUserIdExists(user_id)) {
            logger.warn("Wrong user id");
            throw new ApiRequestException("Invalid user ID!");
        }
        return userDataAccess.insertUserInstanceByUserId(user_id);
    }

    public User getUserById(String userId) {
        UUID user_id = UUID.fromString(userId);
        if (!verifyDataAccess.ifUserIdExists(user_id)){
            logger.warn("Wrong User id");
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

    private void verifyNewUser(User newUser) {
        if (newUser.getUsername().length() < 3)
            throw new ApiRequestException("Wrong username");
        if (newUser.getPassword().length() < 3)
            throw new ApiRequestException("Password it's to short");
        if(userDataAccess.selectUserByUsername(newUser.getUsername()).isPresent())
            throw new ApiRequestException("This username is taken");
        if(verifyDataAccess.ifUserEmailExists(newUser.getEmail()))
            throw new ApiRequestException("This email is taken");
    }
}