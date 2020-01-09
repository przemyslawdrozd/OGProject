package com.example.ogame.controllers;

import com.example.ogame.models.User;
import com.example.ogame.models.UserInstance;
import com.example.ogame.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping(path = "/{id}")
    public UserInstance getUserInstanceByUserId(@PathVariable("id") String id) {
        logger.info("GET userInstanceByUserId - " + id);
        UUID userId = UUID.fromString(id);
        return userService.getUserInstanceByUserId(userId);
    }

    @GetMapping(path = "/{username}/{password}")
    public User loginUserByUsernamePassword(
            @PathVariable String username,
            @PathVariable String password) {
        logger.info("GET loginUserByUsernamePassword: " + username + " " + password);
        return userService.getUserByUsernamePassword(username, password);
    }

    @GetMapping(path = "/user/{id}")
    public User getUserById(@PathVariable("id") String id) {
        logger.info("GET user by id - " + id);
        UUID userId = UUID.fromString(id);
        return userService.getUserById(userId);
    }
}
