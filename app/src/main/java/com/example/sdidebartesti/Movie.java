package com.example.sdidebartesti;

public class Movie {
    int id;
    int year;
    float rating;
    String title;

    public Movie(int idValue, String movieName, float ratingValue, int yearValue){
        id = idValue;
        title = movieName;
        rating = ratingValue;
        year = yearValue;

    }
    public int getId(){return id;}
    public String getTitle(){return title;}
    public float getRating(){return rating;}
    public int getYear(){return year;}
}