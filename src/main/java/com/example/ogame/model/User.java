package com.example.ogame.model;

import java.util.UUID;

public class User {

    private final UUID user_id;
    private final String username;
    private final String password;
    private final String email;

    public User(UUID user_id,
                String username,
                String password,
                String email) {

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
