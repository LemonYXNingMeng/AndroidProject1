package com.example.myapp.vo;

import java.util.Date;

public class Moments {
    String momentsID;
    String createdByUserID;
    String contents;
    boolean isImage;
    String imagePath;
    Date createTime;

    public Moments() {

    }

    public Moments(String imagePath, Date createTime, boolean isImage, String contents, String createdByUserID, String momentsID) {
        this.imagePath = imagePath;
        this.createTime = createTime;
        this.isImage = isImage;
        this.contents = contents;
        this.createdByUserID = createdByUserID;
        this.momentsID = momentsID;
    }

    public String getMomentsID() {
        return momentsID;
    }

    public void setMomentsID(String momentsID) {
        this.momentsID = momentsID;
    }

    public String getCreatedByUserID() {
        return createdByUserID;
    }

    public void setCreatedByUserID(String createdByUserID) {
        this.createdByUserID = createdByUserID;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
