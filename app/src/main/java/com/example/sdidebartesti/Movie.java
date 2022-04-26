package com.example.sdidebartesti;

import java.util.ArrayList;

public class Movie {
    int id;
    String year;
    String title;
    String globalTitle;
    String movieGenre;
    String ageRating;
    ArrayList <String> ratingDescription;
    ArrayList<CastMember> CastList;

    public Movie(int idValue, String movieName, String globalName, String yearValue, ArrayList<CastMember> castMemberList, String genre, String rating, ArrayList<String> ratingDescript){
        id = idValue;
        year = yearValue;
        title = movieName;
        globalTitle = globalName;
        CastList = castMemberList;
        movieGenre = genre;
        ageRating = rating;
        ratingDescription = ratingDescript;
    }
    public int getId(){return id;}
    public String getYear(){return year;}
    public String getTitle(){return title;}
    public String getMovieGenre(){return movieGenre;}
    public String getGlobalTitle(){return globalTitle;}
    public String getAgeRating(){return ageRating;}
    public ArrayList<String> getRatingDescription(){return ratingDescription;}
    public ArrayList<CastMember> getCastList() {
        return(CastList);
    }

    public Boolean getCastMember(String inputName){
        Boolean value = false;
        for(int i = 0; i < CastList.size(); i++){
            String compareCastFullName = CastList.get(i).firstName + " " + CastList.get(i).lastName;
            if(compareCastFullName.contains(inputName)) {
                value = true;
            }
        }
        return(value);
    }
}