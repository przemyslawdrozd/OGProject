package com.example.ogame.controllers;

import com.example.ogame.models.User;
import com.example.ogame.models.UserInstance;
import com.example.ogame.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-api")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        logger.info("Init - userService");
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("GET allUsers called");
        return userService.getAllUsers();
    }

    @PostMapping
    public boolean createNewUser(@RequestBody User newUser) {
        logger.info("POST create a new user - " + newUser);
        return userService.createNewUser(newUser);
    }

    @GetMapping(path = "/{user_id}")
    public UserInstance getUserInstanceByUserId(@PathVariable("user_id") String userId) {
        logger.info("GET userInstanceByUserId - " + userId);
        return userService.getUserInstanceByUserId(userId);
    }

    @GetMapping(path = "/{email}/{password}")
    public User loginUserByEmailPassword(
            @PathVariable String email,
            @PathVariable String password) {
        logger.info("GET loginUserByUsernamePassword: " + email + " " + password);
        return userService.getUserByEmailPassword(email, password);
    }

    @GetMapping(path = "/{user_id}/user")
    public User getUserById(@PathVariable("user_id") String userId) {
        logger.info("GET user by id - " + userId);
        return userService.getUserById(userId);
    }
}
