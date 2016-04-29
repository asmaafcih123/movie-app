package com.example.asmaa.topmovies.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asmaa.topmovies.model.MovieType;
import com.example.asmaa.topmovies.model.Trailers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmaa on 4/25/2016.
 */
public class MovieTypeDB {

        private SQLiteDatabase mDatabase;
        private DBHelper mDbHelper;
        public static final String TAG = "TrailersDB";
        private Context mContext;
        private String[] mAllColumns = {DBHelper.colum_movie_id };

        public MovieTypeDB(Context context) {
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
        public void Add_MovieType(int movieiD,int type) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.colum_type,type);
            values.put(DBHelper.colum_movie_id,movieiD);
            long insertId = mDatabase
                    .insert(DBHelper.table_MovieType, null, values);

        }

        public void delete_Type(int MovieType) {
            mDatabase.delete(DBHelper.table_MovieType,DBHelper.colum_movie_id
                            + " = " +MovieType +" and "+DBHelper.colum_type+"=2"
                    , null);
        }
    public int CheckFavorite(int movieId) {
        int count=0;
        Cursor cursor = mDatabase.query(DBHelper.table_MovieType,mAllColumns,
                DBHelper.colum_movie_id + " = " + movieId +" and "+DBHelper.colum_type + " = " + 2, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            count= cursor.getCount();
            // make sure to close the cursor
            cursor.close();
        }

        return count;
    }
        public List<Integer> getMoviesIDs(int type) {
            List<Integer> movieId = new ArrayList<Integer>();
            Cursor cursor = mDatabase.query(DBHelper.table_MovieType, mAllColumns,
                    DBHelper.colum_type
                            + " = " +type, null, null,
                    null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    movieId.add(cursorTomovie(cursor));
                    cursor.moveToNext();
                }

                // make sure to close the cursor
                cursor.close();
            }

            return movieId;
        }

           protected int cursorTomovie(Cursor cursor) {
            int movieId;
            movieId=cursor.getInt(0);
                return movieId;
        }
    }





