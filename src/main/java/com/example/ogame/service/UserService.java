package com.example.ogame.service;

import com.example.ogame.datasource.DataAccessService;
import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

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
            throw new ApiRequestException("Username or Password is not correct!");
        }
        return dataAccessService.insertUserByUsernamePassword(username, password);
    }
}
