package com.example.asmaa.topmovies.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.asmaa.topmovies.model.Trailers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmaa on 4/25/2016.
 */
public class TrailerDB

{
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    public static final String TAG = "TrailersDB";
    private Context mContext;
    private String[] mAllColumns = {DBHelper.colum_movie_id,DBHelper.colum_Trailer_id,DBHelper.colum_movieKey};

    public TrailerDB(Context context) {
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
    public void Add_Trailer(Trailers Trailer) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.colum_Trailer_id,Trailer.getId());
        values.put(DBHelper.colum_movieKey,Trailer.getMovieKey());
        values.put(DBHelper.colum_movie_id, Trailer.getMovieId());
        long insertId = mDatabase
                .insert(DBHelper.table_Trailer, null, values);

    }

    public void delete_Trailer(int MovieId) {
        mDatabase.delete(DBHelper.table_Trailer,DBHelper.colum_movie_id
                        + " = " +MovieId
                , null);
    }

    public List<Trailers> getAllTrailer(int MovieId) {
        List<Trailers> listTrailer = new ArrayList<Trailers>();
        Trailers Trailer=new Trailers();
        Cursor cursor = mDatabase.query(DBHelper.table_Trailer, mAllColumns,
                DBHelper.colum_movie_id
                        + " = " +MovieId, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Trailer = cursorTomovie(cursor);
                listTrailer.add(Trailer);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }

        return listTrailer;
    }
    public List<Trailers> getAller() {
        List<Trailers> listTrailer = new ArrayList<Trailers>();
        Trailers Trailer=new Trailers();
        Cursor cursor = mDatabase.query(DBHelper.table_Trailer, mAllColumns,
               null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Trailer = cursorTomovie(cursor);
                listTrailer.add(Trailer);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }

        return listTrailer;
    }

    protected Trailers cursorTomovie(Cursor cursor) {
        Trailers Trailer = new Trailers();
        Trailer.setMovieId(cursor.getInt(0));
        Trailer.setId(cursor.getString(1));
        Trailer.setMovieKey(cursor.getString(2));
        return Trailer;
    }
}

