package com.example.myapp.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroupDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "groups.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_GROUPS = "groups";
    public static final String COLUMN_GROUP_ID = "groupID";
    public static final String COLUMN_USER_ID = "createdByUserID";
    public static final String COLUMN_CREATE_TIME = "createTime";
    public static final String COLUMN_CURRENT_NUMBER = "currentNumber";

    private static final String CREATE_TABLE_CHAT_MESSAGES =
            "CREATE TABLE " + TABLE_GROUPS  + "(" +
                    COLUMN_GROUP_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_ID + " TEXT NOT NULL, " +
                    COLUMN_CREATE_TIME  + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    COLUMN_CURRENT_NUMBER + " INTEGER " +
                    ")";

    public GroupDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAT_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        onCreate(db);
    }
}