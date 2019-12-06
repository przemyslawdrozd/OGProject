package com.example.ogame.controller;

import com.example.ogame.model.User;
import com.example.ogame.model.UserInstance;
import com.example.ogame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.System.out;

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

    @GetMapping()
    public List<User> getAllUsers() {
        logger.info("GET allUsers called");
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createNewUser(@RequestBody User newUser) {
        logger.info("POST create a new user - " + newUser);
        userService.createNewUser(newUser);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{user_id}")
    public UserInstance getUserInstanceByUserId(@PathVariable("user_id") String userId) {
        logger.info("GET userInstanceByUserId - " + userId);
        return userService.getUserInstanceByUserId(userId);
    }

    @RequestMapping(
            path = "/{email}/{password}",
            method = RequestMethod.GET
    )
    public User loginUserByEmailPassword(
            @PathVariable String email,
            @PathVariable String password) {
        logger.info("GET loginUserByUsernamePassword: " + email + " " + password);
        return userService.getUserByEmailPassword(email, password);
    }

    @RequestMapping(
            path = "/{user_id}/user",
            method = RequestMethod.GET
    )
    public User getUserById(@PathVariable("user_id") String userId) {
        logger.info("GET user by id - " + userId);
        return userService.getUserById(userId);
    }
}
