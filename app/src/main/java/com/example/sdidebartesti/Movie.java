package com.example.sdidebartesti;

import java.util.ArrayList;

public class Movie {
    int id;
    int year;
    String title;
    String cast;
    ArrayList Cast;

    public Movie(int idValue, String movieName, int yearValue, String castString){
        id = idValue;
        year = yearValue;
        title = movieName;
        cast = castString;

    }
    public int getId(){return id;}
    public int getYear(){return year;}
    public String getTitle(){return title;}
    public String getCast(){return cast;}
}