package com.example.asmaa.topmovies.model;

/**
 * Created by asmaa on 4/22/2016.
 */
public class Trailers
{ String id;
int movieId;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Trailers(String id, String movieKey, int movieId) {
        this.id = id;
        this.movieKey = movieKey;
        this.movieId=movieId;

    }

    public Trailers() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String movieKey;

    public String getMovieKey() {
        return movieKey;
    }

    public void setMovieKey(String movieKey) {
        this.movieKey = movieKey;
    }
}
