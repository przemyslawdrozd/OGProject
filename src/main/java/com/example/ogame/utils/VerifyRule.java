package com.example.ogame.utils;

import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VerifyRule {
    private final Logger logger = LoggerFactory.getLogger(VerifyRule.class);

    private final VerifyDataAccess verifyDataAccess;
    private final UserDataAccess userDataAccess;

    @Autowired
    public VerifyRule(VerifyDataAccess verifyDataAccess,
                      UserDataAccess userDataAccess) {
        this.verifyDataAccess = verifyDataAccess;
        this.userDataAccess = userDataAccess;
    }

    public void verifyUser(UUID userId) {
        if (!verifyDataAccess.ifUserIdExists(userId)) {
            logger.warn("Wrong user id!");
            throw new ApiRequestException("Invalid user ID!");
        }
    }

    public void verifyNewUser(User newUser) {
        if (newUser.getUsername().length() < 3)
            throw new ApiRequestException("Wrong username");
        if (newUser.getPassword().length() < 3)
            throw new ApiRequestException("Password it's to short");
        if(userDataAccess.selectUserByUsername(newUser.getUsername()).isPresent())
            throw new ApiRequestException("This username is taken");
        if(verifyDataAccess.ifUserEmailExists(newUser.getEmail()))
            throw new ApiRequestException("This email is taken");
    }

    public void verifyUser(String username, String password) {
        if (!verifyDataAccess.ifThisUserExists(username, password)) {
            logger.warn("This user does not exists");
            throw new ApiRequestException("Username or Password is not correct!");
        }
    }
}
