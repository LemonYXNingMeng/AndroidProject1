package com.example.myapp.vo;

public class User {
    String ID;
    String name;
    String token;
    String avater;

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

    public User() {
    }

    public User(String name, String ID, String token) {
        this.name = name;
        this.ID = ID;
        this.token = token;
    }
}
