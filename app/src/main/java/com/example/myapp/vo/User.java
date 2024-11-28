package com.example.myapp.vo;

public class User {
    String userID;
    String name;
    String token;
    String avatarPath;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public User() {
    }

    public User(String name, String ID, String token) {
        this.name = name;
        this.userID = ID;
        this.token = token;
    }

    public User(String ID, String name, String token, String avatarPath) {
        this.userID = ID;
        this.name = name;
        this.token = token;
        this.avatarPath = avatarPath;
    }
}
