package com.example.myapp.vo;

import java.util.Date;

public class ChatMessage {
    String chatMessageID;
    String senderID; // userID
    String receiverID; // userID 或 groupID
    String contents;
    String imagePath;
    Date createTime;

    public ChatMessage() {
    }

    public ChatMessage(String chatMessageID, Date createTime, String imagePath, String contents, String receiverID, String senderID) {
        this.chatMessageID = chatMessageID;
        this.createTime = createTime;
        this.imagePath = imagePath;
        this.contents = contents;
        this.receiverID = receiverID;
        this.senderID = senderID;
    }

    public String getChatMessageID() {
        return chatMessageID;
    }

    public void setChatMessageID(String chatMessageID) {
        this.chatMessageID = chatMessageID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
