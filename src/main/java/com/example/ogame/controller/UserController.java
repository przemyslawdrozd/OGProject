package com.example.ogame.controller;

import com.example.ogame.model.*;
import com.example.ogame.service.BuildingsService;
import com.example.ogame.service.ResourceService;
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
    private final ResourceService resourceService;
    private final BuildingsService buildingsService;

    @Autowired
    public UserController(UserService userService,
                          ResourceService resourceService,
                          BuildingsService buildingsService) {

        logger.info("Initializing UserService");
        this.userService = userService;
        this.resourceService = resourceService;
        this.buildingsService = buildingsService;
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
            path = "/{email}/{password}",
            method = RequestMethod.GET
    )
    public User loginUserByEmailPassword(
            @PathVariable String email,
            @PathVariable String password) {
        logger.info("loginUserByUsernamePassword: " + email + " " + password);
        return userService.getUserByEmailPassword(email, password);
    }

    @RequestMapping(
            path = "/{user_id}/user",
            method = RequestMethod.GET
    )
    public User getUserById(
            @PathVariable("user_id") String userId
    ) {
        logger.info("GET user by id: " + userId);
        return userService.getUserById(userId);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/resources")
    public Resources getResourcesByUserId(@PathVariable("user_id") String userID) {
        return resourceService.getResources(userID);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{user_id}/addextra")
    public int addExtraResources(@PathVariable("user_id") String userID) {
        return resourceService.addExtraResources(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/buildings")
    public List<Building> getListOfBuildings(@PathVariable("user_id") String userID) {
        return buildingsService.getBuildings(userID);
    }

    // TODO GET single building
    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/metal")
    public Building getMetalBuilding(@PathVariable("user_id") String userID) {
        return buildingsService.getMetalBuilding(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/cristal")
    public Building getCristalBuilding(@PathVariable("user_id") String userID) {
        return buildingsService.getCristalBuilding(userID);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user_id}/building/deuterium")
    public Building getDeuteriumBuilding(@PathVariable("user_id") String userID) {
        return buildingsService.getDeuteriumBuilding(userID);
    }
}















