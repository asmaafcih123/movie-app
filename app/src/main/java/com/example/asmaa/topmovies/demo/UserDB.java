package com.example.asmaa.topmovies.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asmaa.topmovies.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmaa on 4/27/2016.
 */
public class UserDB {
    public static final String TAG = "UserDB";
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;

    private Context mContext;
    private String[] mAllColumns = {DBHelper.colum_user_id,DBHelper.colum_user_name,DBHelper.colum_e_mail,
            DBHelper.colum_password};

    public UserDB(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void create_Account(User User) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.colum_user_name,User.getUser_name());
        values.put(DBHelper.colum_e_mail, User.getE_mail());
        values.put(DBHelper.colum_password,User.getPassword());
        long insertId = mDatabase
                .insert(DBHelper.table_user, null, values);

    }
     public void delete_User( ) {
        mDatabase.delete(DBHelper.table_user,DBHelper.colum_user_id
                + " !=  1"  , null);
    }

    public int login(String email,String pass) {
       int checkUser=0;
        Cursor cursor = mDatabase.query(DBHelper.table_user, mAllColumns,
                DBHelper.colum_e_mail + " = ? and  "+ DBHelper.colum_password+" = ? ",
                new String[] { String.valueOf(email), String.valueOf(pass) }, null, null, null);
        if (cursor.getCount()==1) {
            checkUser=1;
        }
        cursor.close();
        return checkUser;
    }


    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<User>();
        Cursor cursor = mDatabase.query(DBHelper.table_user, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User User = cursorToUser(cursor);
                Log.d("name1", "> " +cursor.getInt(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(3));
                listUser.add(User);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listUser;
    }
    protected User cursorToUser(Cursor cursor) {
        User User = new User();
        User.setUser_id(cursor.getInt(0));
        User.setUser_name(cursor.getString(1));
        User.setE_mail(cursor.getString(2));
        User.setPassword(cursor.getString(3));
        return User;
    }
}
