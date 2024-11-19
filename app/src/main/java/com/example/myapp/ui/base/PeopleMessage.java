package com.example.myapp.ui.base;

import android.graphics.Bitmap;

public class PeopleMessage {
    private String text;
    private Bitmap image;

    public PeopleMessage(String text, Bitmap image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public Bitmap getImage() {
        return image;
    }
}