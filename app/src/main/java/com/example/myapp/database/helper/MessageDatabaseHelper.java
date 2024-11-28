package com.example.myapp.database.helper;

import android.content.Context;
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
    public static final String COLUMN_IS_SENT_BY_USER = "is_sent_by_user";
    public static final String COLUMN_IS_GROUP = "isGroup";
    public static final String COLUMN_CREATE_TIME = "createTime";

    private static final String CREATE_TABLE_CHAT_MESSAGES =
            "CREATE TABLE " + TABLE_CHAT_MESSAGES + "(" +
                    COLUMN_CHAT_MESSAGE_ID + " TEXT PRIMARY KEY , " +
                    COLUMN_USER_ID + " TEXT NOT NULL, " +
                    COLUMN_FRIEND_ID + " TEXT NOT NULL, " +
                    COLUMN_CONTENTS + " TEXT, " +
                    COLUMN_IMAGE_PATH + " TEXT, " +
                    COLUMN_IS_SENT_BY_USER + " BOOLEAN DEFAULT FALSE, " +
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
}