package com.example.myapp.ui.base;

public class ChatItem {
    private String userName;
    private String messagePreview;

    public ChatItem(String userName, String messagePreview) {
        this.userName = userName;
        this.messagePreview = messagePreview;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessagePreview() {
        return messagePreview;
    }
}




