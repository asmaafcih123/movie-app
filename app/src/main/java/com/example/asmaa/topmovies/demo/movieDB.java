package com.example.asmaa.topmovies.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asmaa.topmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmaa on 4/11/2016.
 */
public class movieDB {
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    Movie.MovieItem movieobj=new Movie.MovieItem();
    public static final String TAG = "movieDB";


    private Context mContext;
    private String[] mAllColumns = {DBHelper.colum_movie_id,DBHelper.colum_original_title,DBHelper.colum_overview,
            DBHelper.colum_poster_path,DBHelper.colum_release_date,DBHelper.colum_vote_average
    };
    private String[] mAllColumnsMovieId = {DBHelper.colum_movie_id};

    public movieDB(Context context) {
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
    public void Add_Movie(Movie.MovieItem movie, int type) {
        ContentValues values = new ContentValues();
        MovieTypeDB MovieTypeDB=new MovieTypeDB(mContext);
        values.put(DBHelper.colum_movie_id,movie.getId());
        values.put(DBHelper.colum_original_title, movie.getOriginal_title());
        values.put(DBHelper.colum_overview,movie.getOverview());
        values.put(DBHelper.colum_poster_path,movie.getPoster_path());
        values.put(DBHelper.colum_release_date, movie.getRelease_date());
        values.put(DBHelper.colum_vote_average,movie.getVote_average());

        long insertId = mDatabase
                .insert(DBHelper.table_movie, null, values);
        MovieTypeDB.Add_MovieType(movie.getId(),type);
    }

    public List<Movie.MovieItem> getAllmovies(int type) {
        List<Movie.MovieItem> listmovies = new ArrayList<Movie.MovieItem>();
        List<Integer> typeList = new ArrayList<Integer>();
        MovieTypeDB MovieTypeDB=new MovieTypeDB(mContext);
        typeList= MovieTypeDB.getMoviesIDs(type);

        for(int i=0;i< typeList.size();i++) {
            Cursor cursor = mDatabase.query(DBHelper.table_movie, mAllColumns,
                    DBHelper.colum_movie_id + " = " + typeList.get(i), null, null,
                    null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Movie.MovieItem movie = cursorTomovie(cursor);
                    listmovies.add(movie);
                    cursor.moveToNext();
                }

                // make sure to close the cursor
                cursor.close();
            }
        }
        return listmovies;
    }

    public int getMovieCount(int type) {

            Cursor cursor = mDatabase.query(DBHelper.table_movie, mAllColumns,
                    DBHelper.colum_type + " = " + type, null, null,
                    null, null);
            int count=cursor.getCount();


                // make sure to close the cursor



        return count;
    }

    public Movie.MovieItem getMovieobj(int movieID) {
        Movie.MovieItem movie=new  Movie.MovieItem ();
        Cursor cursor = mDatabase.query(DBHelper.table_movie, mAllColumns,
                DBHelper.colum_movie_id + " = " + movieID, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                movie = cursorTomovie(cursor);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
          return movie;
    }
   public void delete_movies1(int MovieId,int movieType) {
        MovieTypeDB MovieTypeDB=new MovieTypeDB(mContext);
        MovieTypeDB.delete_Type(movieType);
        mDatabase.delete(DBHelper.table_movie,DBHelper.colum_movie_id
                + " = "+ MovieId
           , null);
    }
    public void delete_movies(int movieId) {
        mDatabase.delete(DBHelper.table_movie,DBHelper.colum_movie_id +" = "+movieId +" and "
        +DBHelper.colum_type + " = " +2
                , null);
    }
    protected Movie.MovieItem cursorTomovie(Cursor cursor) {
        Movie.MovieItem movie = new Movie.MovieItem();
        movie.setId(cursor.getInt(0));
        movie.setOriginal_title(cursor.getString(1));
        movie.setOverview(cursor.getString(2));
        movie.setPoster_path(cursor.getString(3));
        movie.setRelease_date(cursor.getString(4));
        movie.setVote_average(Double.parseDouble(cursor.getString(5)));


        return movie;
    }
}

