package com.example.myapp.vo;

import java.util.Date;

public class Group {
    final static int maxLimit = 200;

    String groupID;
    String createdByUserID;
    Date createTime;
    int currentNumber;

    public Group() {
    }

    public Group(String groupID, String createdByUserID, Date createTime) {
        this.groupID = groupID;
        this.createdByUserID = createdByUserID;
        this.createTime = createTime;
    }

    public Group(String groupID, String createdByUserID, Date createTime, int currentNumber) {
        this.groupID = groupID;
        this.createdByUserID = createdByUserID;
        this.createTime = createTime;
        this.currentNumber = currentNumber;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getCreatedByUserID() {
        return createdByUserID;
    }

    public void setCreatedByUserID(String createdByUserID) {
        this.createdByUserID = createdByUserID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }
}
