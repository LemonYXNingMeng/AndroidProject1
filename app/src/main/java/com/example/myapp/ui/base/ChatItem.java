package com.example.myapp.ui.base;

public class ChatItem {
    private String userID;
    private String userName;
    private String messagePreview;

    public ChatItem(String userID, String userName, String messagePreview) {
        this.userID = userID;
        this.userName = userName;
        this.messagePreview = messagePreview;
    }

    public ChatItem(String userName, String messagePreview) {
        this.userName = userName;
        this.messagePreview = messagePreview;
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
}




