package com.example.myapp.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MessageDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chat_messages.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CHAT_MESSAGES = "chat_messages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_FRIEND_ID = "friend_id";
    public static final String COLUMN_MESSAGE_TEXT = "message_text";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_IS_SENT_BY_USER = "is_sent_by_user";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE_CHAT_MESSAGES =
            "CREATE TABLE " + TABLE_CHAT_MESSAGES + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " TEXT NOT NULL, " +
                    COLUMN_FRIEND_ID + " TEXT NOT NULL, " +
                    COLUMN_MESSAGE_TEXT + " TEXT, " +
                    COLUMN_IMAGE_URI + " TEXT, " +
                    COLUMN_IS_SENT_BY_USER + " BOOLEAN, " +
                    COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
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