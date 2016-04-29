package com.example.asmaa.topmovies.model;

/**
 * Created by asmaa on 4/25/2016.
 */
public class MovieType {
    int  movieId;
    int Type;
    int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public MovieType(int movieId, int type,int  user_id) {
         this.user_id=user_id;
        this.movieId = movieId;
        this.Type = type;
    }
}
