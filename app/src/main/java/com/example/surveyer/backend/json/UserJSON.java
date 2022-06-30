package com.example.surveyer.backend.json;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserJSON {
    @JsonProperty("userName")
    public String userName;
    @JsonProperty("password")
    public String password;
    @JsonProperty("email")
    public String email;

    @JsonCreator
    public UserJSON(@JsonProperty("username") String userName, @JsonProperty("password") String password, @JsonProperty("email") String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    @JsonCreator
    public UserJSON(@JsonProperty("username") String userName, @JsonProperty("password") String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserJSON{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
