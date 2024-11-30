package com.example.myapp.database.helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "userID";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TOKEN = "token";
    public static final String COLUMN_AVATAR_PATH = "avatarPath";

    private static final String CREATE_TABLE_CHAT_MESSAGES =
            "CREATE TABLE " + TABLE_USERS  + "(" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_PHONE + " TEXT NOT NULL, " +
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

    public Cursor getUserByID(String token) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USER_ID, COLUMN_PHONE, COLUMN_NAME, COLUMN_TOKEN, COLUMN_AVATAR_PATH};
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {token};

        return db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public Cursor getAvatarPathByFriendID(String userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USER_ID, COLUMN_PHONE, COLUMN_NAME, COLUMN_TOKEN, COLUMN_AVATAR_PATH};
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userID};

        return db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public Cursor getUserByPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USER_ID, COLUMN_PHONE, COLUMN_NAME, COLUMN_TOKEN, COLUMN_AVATAR_PATH};
        String selection = COLUMN_PHONE + " = ?";
        String[] selectionArgs = {phone};

        return db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    // 模拟服务器端
    public String insertUser(String phone, String name, String password, String avatarPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        // uuid生成
        String userID = UUID.randomUUID().toString();
        // 自然增长
        // String userID

        // 一般不直接存密码
        String userToken = password.length() + password;

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userID);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TOKEN, userToken);
        values.put(COLUMN_AVATAR_PATH, avatarPath);

        long newRowId = db.insert(TABLE_USERS, null, values);

        if (newRowId == -1) {
            return null;
        } else {
            return userID; //
        }
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, null, null, null, null, null, COLUMN_USER_ID+ " ASC");
    }

}