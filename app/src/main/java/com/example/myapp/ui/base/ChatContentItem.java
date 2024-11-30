package com.example.myapp.ui.base;

import android.graphics.Bitmap;
import android.net.Uri;

public class ChatContentItem {
    private String text;
    private Bitmap image;
    private Uri imageUri;
    private Uri avatarUri;
    private boolean isSentByUser;

    public ChatContentItem(String text, Bitmap image, Uri imageUri, Uri avatarUri, boolean isSentByUser) {
        this.text = text;
        this.image = image;
        this.imageUri = imageUri;
        this.avatarUri = avatarUri;
        this.isSentByUser = isSentByUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public boolean isSentByUser() {
        return isSentByUser;
    }

    public void setSentByUser(boolean sentByUser) {
        isSentByUser = sentByUser;
    }
}
