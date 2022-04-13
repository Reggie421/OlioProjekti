package com.example.sdidebartesti;

public class Movie {
    int id;
    int rating;
    String title;

    public Movie(int idValue, String movieName, int ratingValue){
        id = idValue;
        title = movieName;
        rating = ratingValue;

    }
    public int getId(){return id;}
    public String getTitle(){return title;}
    public int getRating(){return rating;}
}