package com.example.ogame.service;

import com.example.ogame.datasource.DataAccessService;
import com.example.ogame.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

//    public void createNewUser(User newUser) {
//        dataAccessService.
//    }
}
