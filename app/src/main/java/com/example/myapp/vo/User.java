package com.example.myapp.vo;

public class User {
    String userID;
    String phone;
    String name;
    String token;
    String avatarPath;

    public User() {
    }

    public User(String userID, String phone, String name, String token, String avatarPath) {
        this.userID = userID;
        this.phone = phone;
        this.name = name;
        this.token = token;
        this.avatarPath = avatarPath;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
