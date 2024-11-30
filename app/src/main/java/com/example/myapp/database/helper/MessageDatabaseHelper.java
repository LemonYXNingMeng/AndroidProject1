package com.example.myapp.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MessageDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chat_messages.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CHAT_MESSAGES = "chat_messages";
    public static final String COLUMN_CHAT_MESSAGE_ID = "chatMessageID";
    public static final String COLUMN_USER_ID = "userID";
    public static final String COLUMN_FRIEND_ID = "friendID";
    public static final String COLUMN_CONTENTS = "contents";
    public static final String COLUMN_IMAGE_PATH = "imagePath";
    //public static final String COLUMN_IS_SENT_BY_USER = "is_sent_by_user";
    public static final String COLUMN_IS_GROUP = "isGroup";
    public static final String COLUMN_CREATE_TIME = "createTime";

    private static final String CREATE_TABLE_CHAT_MESSAGES =
            "CREATE TABLE " + TABLE_CHAT_MESSAGES + "(" +
                    COLUMN_CHAT_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT NOT NULL, " +
                    COLUMN_FRIEND_ID + " TEXT NOT NULL, " +
                    COLUMN_CONTENTS + " TEXT, " +
                    COLUMN_IMAGE_PATH + " TEXT, " +
                    COLUMN_IS_GROUP + " BOOLEAN DEFAULT FALSE, " +
                    COLUMN_CREATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ")";

    public MessageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAT_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT_MESSAGES);
        onCreate(db);
    }


    public boolean insertMessage(String userID, String friendID, String contents, String imagePath, boolean isGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userID);
        values.put(COLUMN_FRIEND_ID, friendID);
        values.put(COLUMN_CONTENTS, contents);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        values.put(COLUMN_IS_GROUP, isGroup ? 1 : 0); // Store boolean as integer

        long result = db.insert(TABLE_CHAT_MESSAGES, null, values);
        return result != -1;
    }

    public Cursor getMessagesByFriendID(String userID,String friendID) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_CHAT_MESSAGES,
                null,
                "(" + COLUMN_USER_ID + "=? AND " + COLUMN_FRIEND_ID + "=?) OR (" + COLUMN_FRIEND_ID + "=? AND " + COLUMN_USER_ID + "=?)",
                new String[]{userID, friendID, userID, friendID},
                null,
                null,
                COLUMN_CREATE_TIME + " ASC"
        );
    }

    public Cursor getAllMessages() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CHAT_MESSAGES, null, null, null, null, null, COLUMN_CREATE_TIME + " ASC");
    }
}