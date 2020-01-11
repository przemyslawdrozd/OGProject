package com.example.ogame.services;

import com.example.ogame.datasource.*;
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
    private final ApplicationUserDao applicationUserDao;
    private final CreatorDataSource creator;
    private final VerifyRule verifyRule;

    @Autowired
    public UserService(UserDataAccess userDataAccess,
                       ApplicationUserDao applicationUserDao,
                       CreatorDataSource creator,
                       VerifyRule verifyRule) {
        this.userDataAccess = userDataAccess;
        this.applicationUserDao = applicationUserDao;
        this.creator = creator;
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
        UUID researchId = UUID.randomUUID();

        creator.insertResearch(researchId);

        creator.insertFleet(fleetId);
        logger.info("New Fleet created");

        creator.insertResources(resourceId);
        logger.info("New resources created");

        creator.insertFacilities(facilitiesId);
        logger.info("New Buildings created");

        creator.insertUser(userId, newUser);
        logger.info("New user created");

        creator.insertNewInstance(userId, resourceId, facilitiesId, fleetId, researchId);
        logger.info("New Instance created");
        return true;
    }

    public User getUserByUsernamePassword(String username, String password) {
        verifyRule.verifyUser(username, password);
        return userDataAccess.selectUserByEmailPassword(username, password);
    }

    public UserInstance getUserInstanceByUserId(UUID userId) {
        verifyRule.verifyUser(userId);
        return userDataAccess.selectUserInstanceByUserId(userId);
    }

    public User getUserById(UUID userId) {
        verifyRule.verifyUser(userId);
        return userDataAccess.selectUserById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao
                .selectUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}