package com.example.ogame.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

public class User {

    private UUID user_id;

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    @Email
    private final String email;

    // To retrieve data and create new user
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("email") String email) {

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

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
