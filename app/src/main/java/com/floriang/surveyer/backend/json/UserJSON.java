package com.floriang.surveyer.backend.json;

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
    @JsonProperty("shownName")
    public String shownName;
    @JsonProperty("anonymous")
    public boolean anonymous;

    @JsonCreator
    public UserJSON(@JsonProperty("username") String userName, @JsonProperty("password") String password, @JsonProperty("email") String email, @JsonProperty("shownName") String shownName, @JsonProperty("anonymous") boolean anonymous) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.shownName = shownName;
        this.anonymous = anonymous;
    }

    @JsonCreator
    public UserJSON(@JsonProperty("shownName") String shownName){
        this.shownName = shownName;
        this.anonymous = true;
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
