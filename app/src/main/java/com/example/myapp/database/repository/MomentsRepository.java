package com.example.myapp.database.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapp.database.helper.MomentsDatabaseHelper;

import java.util.UUID;

public class MomentsRepository {
    private final MomentsDatabaseHelper dbHelper;

    public MomentsRepository(Context context) {
        this.dbHelper = new MomentsDatabaseHelper(context);
    }

    public boolean postMoment(String createdByUserID, String contents, boolean isImage, String imagePath) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String momentsID = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(MomentsDatabaseHelper.COLUMN_MOMENTS_ID, momentsID);
        values.put(MomentsDatabaseHelper.COLUMN_CREATED_USER_ID, createdByUserID);
        values.put(MomentsDatabaseHelper.COLUMN_CONTESTS, contents);
        values.put(MomentsDatabaseHelper.COLUMN_ISIMAGE, isImage ? 1 : 0); // Store boolean as integer
        values.put(MomentsDatabaseHelper.COLUMN_IMAGE_URI, imagePath);

        long result = db.insert(MomentsDatabaseHelper.TABLE_MOMENTS, null, values);
        return result != -1;
    }

    public Cursor getMomentsByUserID(String userID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {MomentsDatabaseHelper.COLUMN_MOMENTS_ID, MomentsDatabaseHelper.COLUMN_CREATED_USER_ID, MomentsDatabaseHelper.COLUMN_CONTESTS, MomentsDatabaseHelper.COLUMN_ISIMAGE, MomentsDatabaseHelper.COLUMN_IMAGE_URI, MomentsDatabaseHelper.COLUMN_CREATE_TIME};
        String selection = MomentsDatabaseHelper.COLUMN_CREATED_USER_ID + " = ?";
        String[] selectionArgs = {userID};

        return db.query(
                MomentsDatabaseHelper.TABLE_MOMENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                MomentsDatabaseHelper.COLUMN_CREATE_TIME + " DESC"
        );
    }

    public Cursor getAllMoments() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(MomentsDatabaseHelper.TABLE_MOMENTS, null, null, null, null, null, MomentsDatabaseHelper.COLUMN_CREATE_TIME + " DESC");
    }
}