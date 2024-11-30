package com.example.myapp.database.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapp.database.helper.GroupDatabaseHelper;

import java.util.UUID;

public class GroupRepository {
    private final GroupDatabaseHelper dbHelper;

    public GroupRepository(Context context) {
        this.dbHelper = new GroupDatabaseHelper(context);
    }

    public String createGroup(String createdByUserID, int currentNumber) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String groupID = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(GroupDatabaseHelper.COLUMN_GROUP_ID, groupID);
        values.put(GroupDatabaseHelper.COLUMN_USER_ID, createdByUserID);
        values.put(GroupDatabaseHelper.COLUMN_CURRENT_NUMBER, currentNumber);

        long result = db.insert(GroupDatabaseHelper.TABLE_GROUPS, null, values);
        return result != -1 ? groupID : null;
    }

    public Cursor getGroupsByUserID(String userID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {GroupDatabaseHelper.COLUMN_GROUP_ID, GroupDatabaseHelper.COLUMN_USER_ID, GroupDatabaseHelper.COLUMN_CREATE_TIME, GroupDatabaseHelper.COLUMN_CURRENT_NUMBER};
        String selection = GroupDatabaseHelper.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userID};

        return db.query(
                GroupDatabaseHelper.TABLE_GROUPS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                GroupDatabaseHelper.COLUMN_CREATE_TIME + " ASC"
        );
    }
}