package com.example.myapp.vo;

import android.graphics.Bitmap;
import android.net.Uri;

public class ChatContentItem {
    private String text;
    private Bitmap image;
    private Uri avatarUri;
    private boolean isSentByUser;
    private Uri imageUri;

    public ChatContentItem(String text, Bitmap image, Uri avatarUri, boolean isSentByUser,Uri imageUri) {
        this.text = text;
        this.image = image;
        this.avatarUri = avatarUri;
        this.isSentByUser = isSentByUser;
        this.imageUri = imageUri;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSentByUser(boolean sentByUser) {
        isSentByUser = sentByUser;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public Bitmap getImage() {
        return image;
    }

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public boolean isSentByUser() {
        return isSentByUser;
    }
}
