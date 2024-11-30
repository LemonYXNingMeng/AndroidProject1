package com.example.myapp.ui.base;

import android.net.Uri;

public class ChatItem {
    private String userID;
    private String userName;
    private String messagePreview;
    private String avatarPath;

    public ChatItem(String userID,String userName,String messagePreview,String avatarPath) {
        this.userID = userID;
        this.avatarPath = avatarPath;
        this.messagePreview = messagePreview;
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessagePreview() {
        return messagePreview;
    }

    public void setMessagePreview(String messagePreview) {
        this.messagePreview = messagePreview;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}




