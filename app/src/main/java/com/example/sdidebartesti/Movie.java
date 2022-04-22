package com.example.sdidebartesti;

import java.util.ArrayList;

public class Movie {
    int id;
    String year;
    String title;
    String globalTitle;
    ArrayList<CastMember> CastList;

    public Movie(int idValue, String movieName, String globalName, String yearValue, ArrayList<CastMember> castMemberList){
        id = idValue;
        year = yearValue;
        title = movieName;
        globalTitle = globalName;
        CastList = castMemberList;

    }
    public int getId(){return id;}
    public String getYear(){return year;}
    public String getTitle(){return title;}
}