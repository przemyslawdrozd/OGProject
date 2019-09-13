package com.example.ogame.controller;

import com.example.ogame.model.*;
import com.example.ogame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api-user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        logger.info("Initializing UserService");
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        logger.info("getAllUsers called");
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createNewUser(@RequestBody @Valid User newUser) {
        logger.info("Create a new user: " + newUser);
        userService.createNewUser(newUser);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}")
    public UserInstance getUserInstanceByUserId(@PathVariable("user_id") String userId) {
        return userService.getUserInstanceByUserId(userId);
    }

    @RequestMapping(
            path = "/{username}/{password}",
            method = RequestMethod.GET
    )
    public User loginUserByUsernamePassword(
            @PathVariable String username,
            @PathVariable String password) {
        logger.info("loginUserByUsernamePassword: " + username + " " + password);
        return userService.getUserByUsernamePassword(username, password);
    }

    // /resources
    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/resources")
    public Resources getResourcesByUserId(@PathVariable("user_id") String userID) {
        return userService.getResourcesByUserId(userID);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{user_id}/addextra")
    public int addExtraResources(@PathVariable("user_id") String userID) {
        return userService.addExtraResources(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/buildings")
    public List<Building> getListOfBuildings(@PathVariable("user_id") String userID) {
        return userService.getBuildings(userID);
    }


}















