package com.example.movieapp;

public class Movie {

    private String title;
    private float rating;
    private String actors;
    private String image;


    public Movie() {}


    public Movie(String title, float rating, String actors, String image) {
        super();
        this.title = title;
        this.rating = rating;
        this.actors = actors;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public String getActors() {
        return actors;
    }
    public void setActors(String actors) {
        this.actors = actors;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


}