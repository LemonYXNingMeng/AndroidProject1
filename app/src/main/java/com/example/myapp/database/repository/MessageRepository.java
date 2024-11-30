package com.example.myapp.database.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.example.myapp.database.helper.MessageDatabaseHelper;
import com.example.myapp.ui.base.ChatContentItem;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MessageRepository {
    private final MessageDatabaseHelper dbHelper;
    private final Context context;

    public MessageRepository(Context context) {
        this.context = context;
        this.dbHelper = new MessageDatabaseHelper(context);
    }

    public boolean insertMessage(String userID, String friendID, String contents, String imagePath,boolean isGroup) {
        return dbHelper.insertMessage(userID, friendID, contents, imagePath, isGroup);
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
                int isGroupInt = cursor.getInt(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_IS_GROUP));
                String createTime = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_CREATE_TIME));

                boolean isSentByUser = false;
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

    public ArrayList<ChatContentItem> getChatContentItemsByFriendID(String userID,String friendAvatarPath,String friendID) {
        ArrayList<ChatContentItem> chatContentItems = new ArrayList<>();
        Cursor cursor = dbHelper.getMessagesByFriendID(userID,friendID);

        if (cursor.moveToFirst()) {
            do {
                String message_userID = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_USER_ID));

                String contents = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_CONTENTS));
                String imageUriStr = cursor.getString(cursor.getColumnIndexOrThrow(MessageDatabaseHelper.COLUMN_IMAGE_PATH));

                boolean isSentByUser = userID.equals(message_userID);
                ChatContentItem item;
                Uri avatarUri = null;
                if(!isSentByUser) avatarUri = Uri.parse(friendAvatarPath);
                if(contents==null || contents.isEmpty()) {
                    Uri imageUri = imageUriStr != null ? Uri.parse(imageUriStr) : null;
                    Bitmap image = loadImageFromPath(imageUriStr);
                    item = new ChatContentItem(contents, image, imageUri, avatarUri, isSentByUser);
                }else {
                    item = new ChatContentItem(contents, null, null, avatarUri, isSentByUser);
                }
                chatContentItems.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return chatContentItems;
    }

    /*public Uri saveImageToExternalStorage(Bitmap bitmap) {
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
    }*/

    public Bitmap createSampleBitmap() {
        // Create a sample bitmap for demonstration purposes
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        // You can draw on the bitmap here if needed
        return bitmap;
    }

    private Bitmap loadImageFromPath(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(imagePath));
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
