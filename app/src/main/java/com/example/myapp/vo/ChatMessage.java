package com.example.myapp.vo;

import java.util.Date;

public class ChatMessage {
    String chatMessageID;
    String userID; // userID
    String friendID;
    String contents;
    String imagePath;
    boolean is_sent_by_user;
    boolean isGroup; // 决定friendID 是 userID 还是 groupID
    Date createTime;

    public ChatMessage() {
    }

    public ChatMessage(Date createTime, boolean isGroup, boolean is_sent_by_user, String imagePath, String contents, String friendID, String userID, String chatMessageID) {
        this.createTime = createTime;
        this.isGroup = isGroup;
        this.is_sent_by_user = is_sent_by_user;
        this.imagePath = imagePath;
        this.contents = contents;
        this.friendID = friendID;
        this.userID = userID;
        this.chatMessageID = chatMessageID;
    }

    public String getChatMessageID() {
        return chatMessageID;
    }

    public void setChatMessageID(String chatMessageID) {
        this.chatMessageID = chatMessageID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public boolean isIs_sent_by_user() {
        return is_sent_by_user;
    }

    public void setIs_sent_by_user(boolean is_sent_by_user) {
        this.is_sent_by_user = is_sent_by_user;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
