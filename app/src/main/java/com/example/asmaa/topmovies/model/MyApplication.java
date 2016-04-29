package com.example.asmaa.topmovies.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmaa on 4/8/2016.
 */
public class MyApplication extends Application {

    public List<Movie.MovieItem> getMovieLis() {
        return movieLis;
    }
    private int MovieId;

    public int isRemmeberMe() {
        return RemmeberMe;
    }

    public void setRemmeberMe(int remmeberMe) {
        RemmeberMe = remmeberMe;
    }

    public int getLogoutFlage() {

        return LogoutFlage;
    }

    public void setLogoutFlage(int logoutFlage) {
        LogoutFlage = logoutFlage;
    }

    public int getRemmeberMe() {
        return RemmeberMe;
    }

    private int LogoutFlage=-1;

    private int RemmeberMe;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
  public String urlTrailer;

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    private  int type=0;
    public boolean isTwoPane() {
        return TwoPane;
    }

    public void setTwoPane(boolean twoPane) {
        TwoPane = twoPane;
    }

    private  boolean TwoPane=false;
    public boolean isIntenetConnection() {
        return intenetConnection;
    }

    public void setIntenetConnection(boolean intenetConnection) {
        this.intenetConnection = intenetConnection;
    }

    boolean intenetConnection=true;
    public int getMovieId()
    {
        return MovieId;
    }

    public void setMovieId(int movieId) {

        MovieId = movieId;
    }

    public void setMovieLis(List<Movie.MovieItem> movieLis) {
        this.movieLis = movieLis;
    }

    List<Movie.MovieItem> movieLis=new ArrayList<Movie.MovieItem> ();
}
