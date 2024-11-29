package com.example.myapp.database.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapp.database.helper.MessageDatabaseHelper;
import com.example.myapp.database.helper.UserDatabaseHelper;
import com.example.myapp.ui.base.ChatItem;
import com.example.myapp.vo.User;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserRepository {
    private UserDatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new UserDatabaseHelper(context);
    }

    public User getUserByID(String userID) {
        //SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getUserByID(userID);
        if (cursor.moveToFirst()) {
            //String id = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_ID));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_PHONE));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_NAME));
            String userToken = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_TOKEN));
            String avatarPath = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_AVATAR_PATH));

            cursor.close();
            return new User(userID,phone,name, userToken, avatarPath);
        }
        cursor.close();
        return null;
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    public User getUserByPhone(String phone) {
        //SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getUserByPhone(phone);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_ID));
            // phone = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_PHONE));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_NAME));
            String userToken = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_TOKEN));
            String avatarPath = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_AVATAR_PATH));

            cursor.close();
            return new User(id,phone,name, userToken, avatarPath);
        }
        cursor.close();
        return null;
    }

    // 模拟服务器生成用户ID
    public String registerUser(String phone,String name, String password,String avatarPath){
        return dbHelper.insertUser(phone,name,password,avatarPath);
    }

    // 模拟服务器
    public User checkUserToken(String account,String password) {
        //String userToken = userID + password;
        User user = null;
        if(isPhone(account)){
            user = getUserByPhone(account);
        }
        if(user==null) user = getUserByID(account);

        String userToken = password.length() + password;
        return user != null && userToken.equals(user.getToken()) ? user : null; // 登录成功返回
    }

    public ArrayList<ChatItem> getAllUsers(String userID) {
        ArrayList<ChatItem> userList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllUsers();
        if (cursor.moveToFirst()) {
            do {
                String friendID = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_ID));
                if(friendID.equals(userID)) continue;
                // 需建立Relation,
                String friendName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_NAME));
                String messagePreview = "Hello!"; // 获取最新消息,MessageRepository
                userList.add(new ChatItem(friendID,friendName,messagePreview));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    public ArrayList<ChatItem> getAllUsers() {
        ArrayList<ChatItem> userList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllUsers();
        if (cursor.moveToFirst()) {
            do {
                String friendID = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_ID));
                String friendName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_NAME));
                String messagePreview = "Hello!"; // 获取最
                userList.add(new ChatItem(friendID,friendName,messagePreview));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }



}
