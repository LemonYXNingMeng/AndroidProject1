package com.example.myapp.vo;

public class GroupItem {
    private String name;
    private String description;

    public GroupItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
