package com.example.myapp.database.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "userID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TOKEN = "token";
    public static final String COLUMN_AVATAR_PATH = "avatarPath";

    private static final String CREATE_TABLE_CHAT_MESSAGES =
            "CREATE TABLE " + TABLE_USERS  + "(" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_TOKEN + " TEXT, " +
                    COLUMN_AVATAR_PATH + " TEXT " +
                    ")";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAT_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}