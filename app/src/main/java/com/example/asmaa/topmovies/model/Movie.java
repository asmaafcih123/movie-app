package com.example.asmaa.topmovies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Movie {

    public static List<MovieItem> ITEMS = new ArrayList<MovieItem>();
    public static final Map<String, MovieItem> ITEM_MAP = new HashMap<String, MovieItem>();
    public static void addItem(MovieItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.getId()), item);
    }

    public static class MovieItem {

            private String poster_path=null;
            private String overview=null;
            private String release_date=null;
            private int id;

            public int getTyp() {
                return typ;
            }

            public void setTyp(int typ) {
                this.typ = typ;
            }

            private int typ;

            private String original_title=null;

            public MovieItem() {
            }

            private double vote_average;

            public MovieItem(String poster_path, String overview, String release_date, int id, String original_title, double vote_average) {
                this.poster_path = poster_path;
                this.overview = overview;
                this.release_date = release_date;
                this.id = id;
                this.original_title = original_title;
                this.vote_average = vote_average;
            }

            public String getPoster_path() {
                return poster_path;
            }

            public void setPoster_path(String poster_path) {
                this.poster_path = poster_path;
            }

            public String getOverview() {
                return overview;
            }

            public void setOverview(String overview) {
                this.overview = overview;
            }

            public String getRelease_date() {
                return release_date;
            }

            public void setRelease_date(String release_date) {
                this.release_date = release_date;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOriginal_title() {
                return original_title;
            }

            public void setOriginal_title(String original_title) {
                this.original_title = original_title;
            }

            public double getVote_average() {
                return vote_average;
            }

            public void setVote_average(double vote_average) {
                this.vote_average = vote_average;
            }

        }

    }

