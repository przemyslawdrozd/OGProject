package com.example.ogame.services;

import com.example.ogame.datasource.FacilitiesDataAccess;
import com.example.ogame.datasource.FleetDataAccess;
import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.models.*;
import com.example.ogame.utils.VerifyRule;
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

    private final UserDataAccess userDataAccess;
    private final FacilitiesDataAccess facilitiesDataAccess;
    private final ApplicationUserDao applicationUserDao;
    private final FleetDataAccess fleetDataAccess;
    private final ResourceDataAccess resourceDataAccess;
    private final VerifyRule verifyRule;

    @Autowired
    public UserService(UserDataAccess userDataAccess,
                       FacilitiesDataAccess facilitiesDataAccess,
                       ApplicationUserDao applicationUserDao,
                       FleetDataAccess fleetDataAccess,
                       ResourceDataAccess resourceDataAccess, VerifyRule verifyRule) {
        this.userDataAccess = userDataAccess;
        this.facilitiesDataAccess = facilitiesDataAccess;
        this.applicationUserDao = applicationUserDao;
        this.fleetDataAccess = fleetDataAccess;
        this.resourceDataAccess = resourceDataAccess;
        this.verifyRule = verifyRule;
    }

    public List<User> getAllUsers() {
        logger.info("userService - getAllUsers");
        return userDataAccess.selectAllUsers();
    }

    public boolean createNewUser(User newUser) {
        verifyRule.verifyNewUser(newUser);

        UUID userId = UUID.randomUUID();
        UUID resourceId = UUID.randomUUID();
        UUID facilitiesId = UUID.randomUUID();
        UUID fleetId = UUID.randomUUID();

        fleetDataAccess.insertFleet(fleetId);
        logger.info("New Fleet created");

        resourceDataAccess.insertResources(resourceId);
        logger.info("New resources created");

        facilitiesDataAccess.insertFacilities(facilitiesId);
        logger.info("New Buildings created");

        userDataAccess.insertUser(userId, newUser);
        logger.info("New user created");

        userDataAccess.insertNewInstance(userId, resourceId, facilitiesId, fleetId);
        logger.info("New Instance created");
        return true;
    }

    public User getUserByUsernamePassword(String username, String password) {
        verifyRule.verifyUser(username, password);
        return userDataAccess.selectUserByEmailPassword(username, password);
    }

    public UserInstance getUserInstanceByUserId(UUID userId) {
        verifyRule.verifyUser(userId);
        return userDataAccess.insertUserInstanceByUserId(userId);
    }

    public User getUserById(UUID userId) {
        verifyRule.verifyUser(userId);
        return userDataAccess.insertUserById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao
                .selectUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}