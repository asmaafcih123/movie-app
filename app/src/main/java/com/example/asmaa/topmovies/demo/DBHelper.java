package com.example.asmaa.topmovies.demo;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by asmaa on 4/11/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = "movieDB";
    private static final String DATABASE_NAME = "movieDB.sqlite";
    private static final int DATABASE_VERSION = 1;

     /*=============================columns,SQL_CREATE_TABLE of the Trailer table=============*/
    public static final String table_Trailer= "Trailer";
    public static final String colum_Trailer_id = "Trailer_id";
    public static final String colum_movieKey= "movieKey";

    private static final String SQL_CREATE_TABLE_TRAILER=
            "CREATE TABLE IF NOT EXISTS Trailer (Trailer_id TEXT PRIMARY KEY NOT NULL," +
                    "movieKey TEXT NOT NULL,Movie_id INTEGER NOT NULL,FOREIGN KEY(Movie_id) REFERENCES movie(movie_id))";

    /*=============================columns,SQL_CREATE_TABLE of the Reviews table=============*/
    public static final String table_Reviews= "Reviews";
    public static final String colum_Reviews_id = "Reviews_id";
    public static final String colum_content= "content";
    public static final String colum_author= "author";
    public static final String colum_url = "url";

    private static final String SQL_CREATE_TABLE_Reviews=
            "CREATE TABLE IF NOT EXISTS Reviews (Reviews_id TEXT PRIMARY KEY  NOT NULL,movie_id INTEGER NOT NULL ," +
                    "url TEXT NOT NULL,author TEXT NOT NULL,content TEXT NOT NULL," +
                    "FOREIGN KEY(Movie_id) REFERENCES movie(movie_id))";

    /*=============================columns,SQL_CREATE_TABLE of the movie table=============*/
    public static final String table_movie= "movie";
    public static final String colum_movie_id = "movie_id";
    public static final String colum_poster_path= "poster_path";
    public static final String colum_overview = "overview";
    public static final String colum_release_date= "release_date";
    public static final String colum_original_title = "original_title";
    public static final String colum_vote_average ="vote_average";

    private static final String SQL_CREATE_TABLE_movie=

            "CREATE TABLE IF NOT EXISTS movie (movie_id INTEGER  PRIMARY KEY NOT NULL ," +
                    "poster_path TEXT NOT NULL,release_date TEXT NOT NULL," +
                    "overview TEXT NOT NULL,original_title TEXT NOT NULL," +
                    "vote_average TEXT NOT NULL)";
    /*=============================columns,SQL_CREATE_TABLE of the MovieType table=============*/
    public static final String table_MovieType= "MovieType";
    public static final String colum_type= "type";

    private static final String SQL_CREATE_TABLE_MovieType=
            "CREATE TABLE IF NOT EXISTS MovieType (MovieType_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "type INTEGER NOT NULL,Movie_id INTEGER NOT NULL ," +
                    "FOREIGN KEY(Movie_id) REFERENCES movie(movie_id))";


    /*===========================columns of the user table============*/
    public static final String table_user = "user";
    public static final String colum_user_id = "user_id";
    public static final String colum_user_name = "user_name";
    public static final String colum_e_mail = "e_mail";
    public static final String colum_password = "password";

    private static final String SQL_CREATE_TABLE_user =

            "CREATE TABLE IF NOT EXISTS user (`user_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                    ",`user_name` TEXT NOT NULL,`e_mail` TEXT NOT NULL,password TEXT NOT NULL)";

    /*===========================columns of the workout table============*/



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        try {
            database.execSQL(SQL_CREATE_TABLE_movie);
            database.execSQL(SQL_CREATE_TABLE_TRAILER);
            database.execSQL(SQL_CREATE_TABLE_Reviews);
            database.execSQL(SQL_CREATE_TABLE_MovieType);
            database.execSQL(SQL_CREATE_TABLE_user);
            database.execSQL("INSERT INTO user VALUES (1,'null','null','null');");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db ,int oldVersion,int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        // clear all data

        db.execSQL("DROP TABLE IF EXISTS " + table_movie);
        db.execSQL("DROP TABLE IF EXISTS " + table_Reviews);
        db.execSQL("DROP TABLE IF EXISTS " + table_MovieType);
        db.execSQL("DROP TABLE IF EXISTS " + table_Trailer);
        db.execSQL("DROP TABLE IF EXISTS " +SQL_CREATE_TABLE_user);


        // recreate the tables
        onCreate(db);
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

}
