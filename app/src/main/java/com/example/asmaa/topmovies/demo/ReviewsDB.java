package com.example.asmaa.topmovies.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asmaa.topmovies.model.Movie;
import com.example.asmaa.topmovies.model.Reviews;
import com.example.asmaa.topmovies.model.Trailers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmaa on 4/25/2016.
 */
public class ReviewsDB {
        private SQLiteDatabase mDatabase;
        private DBHelper mDbHelper;
        Reviews Reviewobj=new Reviews();
        public static final String TAG = "ReviewsDB";


        private Context mContext;
        private String[] mAllColumns = {DBHelper.colum_movie_id,DBHelper.colum_Reviews_id,DBHelper.colum_author,
                DBHelper.colum_content,DBHelper.colum_url};
        private String[] mAllColumnsMovieId = {DBHelper.colum_movie_id};

        public ReviewsDB(Context context) {
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
        public void Add_Review(Reviews reviews) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.colum_Reviews_id,reviews.getId());
            values.put(DBHelper.colum_movie_id,reviews.getMovieId());
            values.put(DBHelper.colum_author, reviews.getAuthor());
            values.put(DBHelper.colum_content,reviews.getContent());
            values.put(DBHelper.colum_url,reviews.getUrl());
            long insertId = mDatabase
                    .insert(DBHelper.table_Reviews, null, values);

        }

    public void delete_Review(int MovieId) {
        mDatabase.delete(DBHelper.table_Reviews,DBHelper.colum_movie_id
                        + " = " +MovieId
                , null);
    }

        public List<Reviews> getAllReview(int MovieId) {
            List<Reviews> listReview = new ArrayList<Reviews>();
            Reviews Reviews=new Reviews();
            Cursor cursor = mDatabase.query(DBHelper.table_Reviews, mAllColumns,
                    DBHelper.colum_movie_id
                            + " = " +MovieId, null, null,
                    null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Reviews = cursorTomovie(cursor);
                    listReview.add(Reviews);
                    cursor.moveToNext();
                }

                // make sure to close the cursor
                cursor.close();
            }

            return listReview;
        }

    public List<Reviews> getAllReview1() {
        List<Reviews> listReview = new ArrayList<Reviews>();
        Reviews Reviews=new Reviews();
        Cursor cursor = mDatabase.query(DBHelper.table_Reviews, mAllColumns,
                null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Reviews = cursorTomovie(cursor);
                listReview.add(Reviews);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }

        return listReview;
    }




    protected Reviews cursorTomovie(Cursor cursor) {
            Reviews Review = new Reviews();
            Review.setMovieId(cursor.getInt(0));
            Review.setId(cursor.getString(1));
            Review.setAuthor(cursor.getString(2));
            Review.setContent(cursor.getString(3));
            Review.setUrl(cursor.getString(4));
              return Review;
        }
    }

