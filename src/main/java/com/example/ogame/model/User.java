package com.example.ogame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class User {

    private UUID user_id;

    @NotBlank
    private final String username;

    @NotBlank
    private String password;

    @NotBlank
    @Email
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

    public User(@JsonProperty("user_id") UUID user_id,
                @JsonProperty("username") String username,
                @JsonProperty("email") String email) {

        this.user_id = user_id;
        this.username = username;
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
