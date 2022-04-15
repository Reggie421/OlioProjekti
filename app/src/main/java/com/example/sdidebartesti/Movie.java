package com.example.sdidebartesti;

public class Movie {
    int id;
    int year;
    String title;

    public Movie(int idValue, String movieName, int yearValue){
        id = idValue;
        title = movieName;
        year = yearValue;

    }
    public int getId(){return id;}
    public String getTitle(){return title;}
    public int getYear(){return year;}
}