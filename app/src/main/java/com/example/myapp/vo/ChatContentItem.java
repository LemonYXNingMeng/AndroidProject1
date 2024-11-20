package com.example.myapp.vo;

import android.graphics.Bitmap;
import android.net.Uri;

public class ChatContentItem {
    private String text;
    private Bitmap image;
    private Uri avatarUri;
    private boolean isSentByUser; // true if sent by user, false if received

    public ChatContentItem(String text, Bitmap image, Uri avatarUri, boolean isSentByUser) {
        this.text = text;
        this.image = image;
        this.avatarUri = avatarUri;
        this.isSentByUser = isSentByUser;
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
