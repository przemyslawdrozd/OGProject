package com.example.ogame.service;

import com.example.ogame.datasource.DataAccessService;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.model.Resources;
import com.example.ogame.model.User;
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
        UUID uuid = UUID.randomUUID();
        String user_id = uuid.toString();

        dataAccessService.insertUser(uuid, newUser);
    }

    public User getUserByUsernamePassword(String username, String password) {

        // if user dont exists
        if (!dataAccessService.ifThisUserExists(username, password)) {
            logger.debug("This user does not exists");
            throw new ApiRequestException("Username or Password is not correct!");
        }
        return dataAccessService.insertUserByUsernamePassword(username, password);
    }

    public Resources getResourcesByUserId(String userID) {
        UUID user_id = UUID.fromString(userID);

        // Verify User ID
        if (!dataAccessService.ifUserIdExists(user_id)){
            throw new ApiRequestException("Invalid user ID!");
        }

        return dataAccessService.getResourcesByUserId(user_id);
    }
}














