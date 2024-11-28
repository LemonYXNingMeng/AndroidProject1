package com.example.myapp.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MomentsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moments.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MOMENTS = "moments";
    public static final String COLUMN_MOMENTS_ID = "momentsID";
    public static final String COLUMN_CREATED_USER_ID = "createdByUserID";
    public static final String COLUMN_CONTESTS = "contents";
    public static final String COLUMN_ISIMAGE = "isImage";
    public static final String COLUMN_IMAGE_URI = "imagePath";
    public static final String COLUMN_CREATE_TIME = "createTime";

    private static final String CREATE_TABLE_CHAT_MESSAGES =
            "CREATE TABLE " + TABLE_MOMENTS + "(" +
                    COLUMN_MOMENTS_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_CREATED_USER_ID + " TEXT NOT NULL, " +
                    COLUMN_CONTESTS + " TEXT, " +
                    COLUMN_ISIMAGE + " BOOLEAN, " +
                    COLUMN_IMAGE_URI  + "  TEXT, " +
                    COLUMN_CREATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ")";

    public MomentsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAT_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOMENTS);
        onCreate(db);
    }
}