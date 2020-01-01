package com.example.ogame.services;

import com.example.ogame.models.User;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<User> selectUserByUsername(String username);
}