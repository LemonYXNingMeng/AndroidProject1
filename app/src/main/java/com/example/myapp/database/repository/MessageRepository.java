package com.example.myapp.database.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.example.myapp.database.helper.MessageDatabaseHelper;

import java.io.OutputStream;
import java.util.ArrayList;

public class MessageRepository {
    private final MessageDatabaseHelper dbHelper;
    private final Context context;

    public MessageRepository(Context context) {
        this.context = context;
        this.dbHelper = new MessageDatabaseHelper(context);
    }

    public boolean insertMessage(String chatMessageID, String userID, String friendID, String contents, String imagePath, boolean isSentByUser, boolean isGroup) {
        return dbHelper.insertMessage(chatMessageID, userID, friendID, contents, imagePath, isSentByUser, isGroup);
    }

    public ArrayList<String> getAllMessages() {
        ArrayList<String> messageList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllMessages();

        if (cursor.moveToFirst()) {
            do {
                String chatMessageID = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_CHAT_MESSAGE_ID));
                String userID = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_USER_ID));
                String friendID = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_FRIEND_ID));
                String contents = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_CONTENTS));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_IMAGE_PATH));
                int isSentByUserInt = cursor.getInt(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_IS_SENT_BY_USER));
                int isGroupInt = cursor.getInt(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_IS_GROUP));
                String createTime = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_CREATE_TIME));

                boolean isSentByUser = isSentByUserInt == 1;
                boolean isGroup = isGroupInt == 1;

                // Format the message as you need
                String message = "ID: " + chatMessageID + "\n"
                        + "User ID: " + userID + "\n"
                        + "Friend ID: " + friendID + "\n"
                        + "Contents: " + contents + "\n"
                        + "Image Path: " + imagePath + "\n"
                        + "Is Sent By User: " + isSentByUser + "\n"
                        + "Is Group: " + isGroup + "\n"
                        + "Create Time: " + createTime + "\n";

                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return messageList;
    }

    public Uri saveImageToExternalStorage(Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "sample.jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (imageUri != null) {
            try (OutputStream outputStream = context.getContentResolver().openOutputStream(imageUri)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                return imageUri;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Bitmap createSampleBitmap() {
        // Create a sample bitmap for demonstration purposes
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        // You can draw on the bitmap here if needed
        return bitmap;
    }
}
