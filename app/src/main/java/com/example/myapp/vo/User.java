package com.example.myapp.vo;

public class User {
    String ID;
    String name;
    String token;
    String avatarPath;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
        this.ID = ID;
        this.token = token;
    }

    public User(String ID, String name, String token, String avatarPath) {
        this.ID = ID;
        this.name = name;
        this.token = token;
        this.avatarPath = avatarPath;
    }
}
