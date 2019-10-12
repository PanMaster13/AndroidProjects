package com.example.task1;

public class Movie {
    private String title;
    private String genre;
    private String duration;

    private int image;

    public Movie() {
    }

    public Movie(String title, String genre, String duration, int image) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
