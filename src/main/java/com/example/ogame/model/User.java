package com.example.ogame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    private final UUID user_id;
    private final String username;
    private final String password;
    private final String email;

    public User(@JsonProperty("user_id") UUID user_id,
                @JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("email") String email) {

        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
