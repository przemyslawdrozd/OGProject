package com.example.ogame.controller;

import com.example.ogame.model.User;
import com.example.ogame.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api-user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createNewUser(@RequestBody @Valid User newUser) {
        userService.createNewUser(newUser);
    }

    @RequestMapping(
            path = "/{username}/{password}",
            method = RequestMethod.GET
    )
    public User loginUserByUsernamePassword(
            @PathVariable String username,
            @PathVariable String password) {
        return userService.getUserByUsernamePassword(username, password);
    }

}
