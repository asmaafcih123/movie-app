package com.example.asmaa.topmovies.model;

/**
 * Created by asmaa on 4/22/2016.
 */
public class Reviews {
    String author;

    public Reviews(String author, String url, String content, String id,int movieId) {
        this.author = author;
        this.url = url;
        this.content = content;
        this.id = id;
        this.movieId=movieId;
    }
    public Reviews() {
            }

    String url;
    String content;
    String id;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    int movieId;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
